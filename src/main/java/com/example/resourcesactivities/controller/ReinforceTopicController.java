package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.ReinforceTopicDTO;
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

    @GetMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<List<ReinforceTopicDTO>> getTopicsByUserId(@PathVariable Long userId,@PathVariable Integer courseId) {
        List<ReinforceTopicDTO> topics = reinforceTopicService.getTopicsByUserId(userId,courseId);
        return ResponseEntity.ok(topics);
    }

}
