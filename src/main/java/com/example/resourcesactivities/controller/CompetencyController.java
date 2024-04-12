package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.Competency;
import com.example.resourcesactivities.repository.CompetencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competencies")
public class CompetencyController {
    @Autowired
    private CompetencyRepository competencyRepository;

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
            existingCompetency.setTopic(competencyDetails.getTopic());
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