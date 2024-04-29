package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
@CrossOrigin(originPatterns = "*")
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private MyResourceRepository myResourceRepository;
    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable(value = "id") Integer topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        return topic.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/resources")
    public ResponseEntity<List<MyResource>> getResourcesByTopicId(@PathVariable(value = "id") Integer topicId) {
        List<MyResource> resources = myResourceRepository.findByTopicId(topicId);
        if (resources != null) {
            return ResponseEntity.ok(resources);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        Topic savedTopic = topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTopic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable(value = "id") Integer topicId,
                                             @RequestBody Topic topicDetails) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic existingTopic = optionalTopic.get();
            existingTopic.setName(topicDetails.getName());
            existingTopic.setDescription(topicDetails.getDescription());
            existingTopic.setCourse(topicDetails.getCourse());
            existingTopic.setLearningUnit(topicDetails.getLearningUnit());
            existingTopic.setCompetence(topicDetails.getCompetence());
            existingTopic.setStatus(topicDetails.getStatus());
            Topic updatedTopic = topicRepository.save(existingTopic);
            return ResponseEntity.ok(updatedTopic);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable(value = "id") Integer topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (topic.isPresent()) {
            topicRepository.delete(topic.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
