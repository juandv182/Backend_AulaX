package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.LearningUnit;
import com.example.resourcesactivities.repository.LearningUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/learning_units")
@CrossOrigin(originPatterns = "*")
public class LearningUnitController {
    @Autowired
    private LearningUnitRepository learningUnitRepository;

    @GetMapping
    public List<LearningUnit> getAllLearningUnits() {
        return learningUnitRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningUnit> getCompetencyById(@PathVariable(value = "id") Integer learningUnitId) {
        Optional<LearningUnit> learningUnit = learningUnitRepository.findById(learningUnitId);
        return learningUnit.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LearningUnit> createCompetency(@RequestBody LearningUnit competency) {
        LearningUnit savedCompetency = learningUnitRepository.save(competency);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompetency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningUnit> updateCompetency(@PathVariable(value = "id") Integer competencyId,
                                                       @RequestBody LearningUnit learningUnitDetails) {
        Optional<LearningUnit> optionalLearningUnit = learningUnitRepository.findById(competencyId);
        if (optionalLearningUnit.isPresent()) {
            LearningUnit existingLearningUnit = optionalLearningUnit.get();
            existingLearningUnit.setName(learningUnitDetails.getName());
            existingLearningUnit.setDescription(learningUnitDetails.getDescription());
            existingLearningUnit.setStatus(learningUnitDetails.getStatus());
            LearningUnit updatedCompetency = learningUnitRepository.save(existingLearningUnit);
            return ResponseEntity.ok(updatedCompetency);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearningUnit(@PathVariable(value = "id") Integer competencyId) {
        Optional<LearningUnit> competency = learningUnitRepository.findById(competencyId);
        if (competency.isPresent()) {
            learningUnitRepository.delete(competency.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}