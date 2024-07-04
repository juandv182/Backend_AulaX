package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.MyResourceDTO;
import com.example.resourcesactivities.dto.ResourceFileDTO;
import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.repository.TopicRepository;
import com.example.resourcesactivities.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private TopicService service;

    @GetMapping
    public List<TopicDTO> getAllTopics() {
        return service.getAllTopics();
    }

    @GetMapping("/unit/{id}")
    public List<TopicDTO> getAllTopicsByUnitId(@PathVariable(value = "id") Integer id) {
        return service.getAllTopicsByUnitId(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopicById(@PathVariable(value = "id") Integer topicId) {
        TopicDTO topic = service.getTopicById(topicId);
        return topic != null ? ResponseEntity.ok().body(topic) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/resources")
    public ResponseEntity<List<MyResourceDTO>> getResourcesByTopicId(@PathVariable(value = "id") Integer topicId) {
        List<MyResourceDTO> recursos = service.getResourcesByTopicId(topicId);
        return recursos != null ? ResponseEntity.ok(recursos) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody TopicDTO topicDTO) {
        TopicDTO savedTopic = service.createTopic(topicDTO);
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
        boolean isDeleted = service.deleteTopic(topicId);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
