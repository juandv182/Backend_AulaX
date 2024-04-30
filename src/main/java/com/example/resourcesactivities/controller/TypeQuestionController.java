package com.example.resourcesactivities.controller;


import com.example.resourcesactivities.model.TypeQuestion;
import com.example.resourcesactivities.repository.TypeQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/type_questions")
@CrossOrigin(originPatterns = "*")
public class TypeQuestionController {
    @Autowired
    private TypeQuestionRepository typeQuestionRepository;
    @GetMapping
    public List<TypeQuestion> getAllTypeQuestions() {
        return typeQuestionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeQuestion> getTypeQuestionById(@PathVariable(value = "id") Integer typeQuestionId) {
        Optional<TypeQuestion> typeQuestion = typeQuestionRepository.findById(typeQuestionId);
        return typeQuestion.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TypeQuestion> createTypeQuestion(@RequestBody TypeQuestion typeQuestionId) {
        TypeQuestion savedTypeQuestion = typeQuestionRepository.save(typeQuestionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTypeQuestion);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeQuestion(@PathVariable(value = "id") Integer typeQuestionId) {
        Optional<TypeQuestion> typeQuestion = typeQuestionRepository.findById(typeQuestionId);
        if (typeQuestion.isPresent()) {
            typeQuestionRepository.delete(typeQuestion.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
