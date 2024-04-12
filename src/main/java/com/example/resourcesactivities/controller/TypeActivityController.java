package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.TypeActivity;
import com.example.resourcesactivities.repository.TypeActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/types_activities")
public class TypeActivityController {
    @Autowired
    private TypeActivityRepository typeActivityRepository;

    @GetMapping
    public List<TypeActivity> getAllTypeActivities() {
        return typeActivityRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeActivity> getTypeActivityById(@PathVariable(value = "id") Integer typeActivityId) {
        Optional<TypeActivity> typeActivity = typeActivityRepository.findById(typeActivityId);
        return typeActivity.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TypeActivity> createTypeActivity(@RequestBody TypeActivity typeActivity) {
        TypeActivity savedTypeActivity = typeActivityRepository.save(typeActivity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTypeActivity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeActivity> updateTypeActivity(@PathVariable(value = "id") Integer typeActivityId,
                                                           @RequestBody TypeActivity typeActivityDetails) {
        Optional<TypeActivity> optionalTypeActivity = typeActivityRepository.findById(typeActivityId);
        if (optionalTypeActivity.isPresent()) {
            TypeActivity existingTypeActivity = optionalTypeActivity.get();
            existingTypeActivity.setName(typeActivityDetails.getName());
            existingTypeActivity.setDescription(typeActivityDetails.getDescription());
            existingTypeActivity.setStatus(typeActivityDetails.getStatus());
            TypeActivity updatedTypeActivity = typeActivityRepository.save(existingTypeActivity);
            return ResponseEntity.ok(updatedTypeActivity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeActivity(@PathVariable(value = "id") Integer typeActivityId) {
        Optional<TypeActivity> typeActivity = typeActivityRepository.findById(typeActivityId);
        if (typeActivity.isPresent()) {
            typeActivityRepository.delete(typeActivity.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
