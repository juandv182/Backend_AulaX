package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.CourseDTO;
import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.model.Competency;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.CompetencyRepository;
import com.example.resourcesactivities.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/competencies")
@CrossOrigin(originPatterns = "*")
public class CompetencyController {
    @Autowired
    private CompetencyRepository competencyRepository;
    @Autowired
    private TopicRepository topicRepository;

    @GetMapping
    public List<Competency> getAllCompetencies() {
        return competencyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competency> getCompetencyById(@PathVariable(value = "id") Integer competencyId) {
        Optional<Competency> competency = competencyRepository.findById(competencyId);
        return competency.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/topics")
    public ResponseEntity<List<TopicDTO>> getTopicsByCompetencyId(@PathVariable(value = "id") Integer competencyId) {
        Optional<Competency> competency = competencyRepository.findById(competencyId);
        if (competency.isPresent()) {
            List<Topic> topics = topicRepository.findAllByCompetence(competency.get());
            List<TopicDTO> topicsDTO =topics.stream().map(t->{
                CourseDTO cdto = CourseDTO.builder()
                        .id(t.getCourse().getId())
                        .name(t.getCourse().getName())
                        .description(t.getCourse().getDescription())
                        .build();
                return TopicDTO.builder()
                        .id(t.getId())
                        .name(t.getName())
                        .description((t.getDescription()))
                        .course(cdto)
                        .learningUnit(t.getLearningUnit())
                        .competence(t.getCompetence())
                        .status(t.getStatus())
                        .createdAt(t.getCreatedAt())
                        .updatedAt(t.getUpdatedAt())
                        .build();
            }).collect(Collectors.toList());
            return ResponseEntity.ok(topicsDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Competency> createCompetency(@RequestBody Competency competency) {
        Competency savedCompetency = competencyRepository.save(competency);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompetency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competency> updateCompetency(@PathVariable(value = "id") Integer competencyId,
                                                       @RequestBody Competency competencyDetails) {
        Optional<Competency> optionalCompetency = competencyRepository.findById(competencyId);
        if (optionalCompetency.isPresent()) {
            Competency existingCompetency = optionalCompetency.get();
            existingCompetency.setName(competencyDetails.getName());
            existingCompetency.setDescription(competencyDetails.getDescription());
            existingCompetency.setStatus(competencyDetails.getStatus());
            Competency updatedCompetency = competencyRepository.save(existingCompetency);
            return ResponseEntity.ok(updatedCompetency);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetency(@PathVariable(value = "id") Integer competencyId) {
        Optional<Competency> competency = competencyRepository.findById(competencyId);
        if (competency.isPresent()) {
            competencyRepository.delete(competency.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}