package com.example.resourcesactivities.service;

import com.example.resourcesactivities.model.*;
import com.example.resourcesactivities.repository.AdaptativeFileHistoryRepository;
import com.example.resourcesactivities.repository.FileRepository;
import com.example.resourcesactivities.repository.QuizzRepository;
import com.example.resourcesactivities.repository.ReinforceTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdaptativeFileHistoryService {
    @Autowired
    private AdaptativeFileHistoryRepository adaptativeFileHistoryRepository;
    @Autowired
    private ReinforceTopicRepository reinforceTopicRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private QuizzRepository quizzRepository;

    public void saveHistory(Integer rtId ,Integer fId, Integer qId) {
        Optional<ReinforceTopic> rt = reinforceTopicRepository.findById(rtId);
        Optional<ResourceFile> file = fileRepository.findById(fId);
        Optional<Quizz> quizz = quizzRepository.findById(qId);
        AdaptativeFileHistory history = new AdaptativeFileHistory();
        history.setReinforceTopic(rt.orElseThrow());
        history.setFile(file.orElseThrow());
        history.setQuizzDado(quizz.orElseThrow());
        history.setViewedAt(LocalDateTime.now());
        adaptativeFileHistoryRepository.save(history);
    }

    public List<AdaptativeFileHistory> getHistoryByUser(User user) {
        return adaptativeFileHistoryRepository.findByReinforceTopicUser(user);
    }
}
