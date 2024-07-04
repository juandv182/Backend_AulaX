package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.*;
import com.example.resourcesactivities.model.AdaptativeFileHistory;
import com.example.resourcesactivities.model.User;
import com.example.resourcesactivities.repository.UserRepository;
import com.example.resourcesactivities.service.AdaptativeFileHistoryService;
import com.example.resourcesactivities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adaptive-history")
public class AdaptativeFileHistoryController {
    @Autowired
    private AdaptativeFileHistoryService adaptativeFileHistoryService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/save")
    public ResponseEntity<String> saveAdaptiveFileHistory(
            @RequestParam Integer rtId,
            @RequestParam Integer fId,
            @RequestParam Integer qId) {
        try {
            adaptativeFileHistoryService.saveHistory(rtId, fId, qId);
            return ResponseEntity.ok("Historial guardado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el historial.");
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdaptativeFileHistoryDTO>> getHistoryByUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        List<AdaptativeFileHistory> historyList = adaptativeFileHistoryService.getHistoryByUser(user.orElseThrow());
        List<AdaptativeFileHistoryDTO> historyDTOList = historyList.stream().map(h-> {
            ResourceFileDTO f = ResourceFileDTO.builder()
                    .id(h.getFile().getId())
                    .url(h.getFile().getUrl())
                    .build();
            TopicDTO topicDTO=TopicDTO.builder()
                    .id(h.getReinforceTopic().getTopic().getId())
                    .name(h.getReinforceTopic().getTopic().getName())
                    .build();
            ReinforceTopicDTO r=ReinforceTopicDTO.builder()
                    .id(h.getReinforceTopic().getId())
                    .topic(topicDTO)
                    .estado(h.getReinforceTopic().getEstado())
                    .build();
            QuizzDTO q= QuizzDTO.builder()
                    .id(h.getQuizzDado().getId())
                    .nota(h.getQuizzDado().getNota())
                    .name(h.getQuizzDado().getName())
                    .build();
            return AdaptativeFileHistoryDTO.builder()
                    .file(f)
                    .id(h.getId())
                    .reinforceTopic(r)
                    .quizzDado(q)
                    .viewedAt(h.getViewedAt())
                    .build();
        }).collect(Collectors.toList());
        return ResponseEntity.ok(historyDTOList);
    }


}
