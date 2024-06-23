package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.AdaptativeFileHistoryDTO;
import com.example.resourcesactivities.dto.UserDTO;
import com.example.resourcesactivities.model.AdaptativeFileHistory;
import com.example.resourcesactivities.model.User;
import com.example.resourcesactivities.repository.UserRepository;
import com.example.resourcesactivities.service.AdaptativeFileHistoryService;
import com.example.resourcesactivities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdaptativeFileHistoryDTO>> getHistoryByUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        List<AdaptativeFileHistory> historyList = adaptativeFileHistoryService.getHistoryByUser(user.orElseThrow());
        List<AdaptativeFileHistoryDTO> historyDTOList = historyList.stream().map(h->
                                AdaptativeFileHistoryDTO.builder()
                                        .file(h.getFile())
                                        .nota(h.getNota())
                                        .id(h.getId())
                                        .reinforceTopic(h.getReinforceTopic())
                                        .viewedAt(h.getViewedAt())
                                        .build()
                        ).collect(Collectors.toList());
        return ResponseEntity.ok(historyDTOList);
    }


}
