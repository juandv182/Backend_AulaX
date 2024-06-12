package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.service.ReinforceTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reinforce-topics")
public class ReinforceTopicController {
    @Autowired
    private ReinforceTopicService reinforceTopicService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TopicDTO>> getTopicsByUserId(@PathVariable Long userId) {
        List<TopicDTO> topics = reinforceTopicService.getTopicsByUserId(userId);
        return ResponseEntity.ok(topics);
    }

}
