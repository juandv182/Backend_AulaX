package com.example.resourcesactivities.controller;


import com.example.resourcesactivities.model.Alternative;
import com.example.resourcesactivities.repository.AlternativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alternatives")
public class AlternativeController {
    @Autowired
    private AlternativeRepository alternativeRepository;
    @GetMapping
    public List<Alternative> getAllAlternatives() {
        return alternativeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alternative> getAlternativeById(@PathVariable(value = "id") Integer alternatieId) {
        Optional<Alternative> alternative = alternativeRepository.findById(alternatieId);
        return alternative.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Alternative> createAlternative(@RequestBody Alternative alternative) {
        Alternative savedAlternative = alternativeRepository.save(alternative);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAlternative);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlternative(@PathVariable(value = "id") Integer alternatieId) {
        Optional<Alternative> alternative = alternativeRepository.findById(alternatieId);
        if (alternative.isPresent()) {
            alternativeRepository.delete(alternative.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
