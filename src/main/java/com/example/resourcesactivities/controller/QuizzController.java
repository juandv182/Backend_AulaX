package com.example.resourcesactivities.controller;



import com.example.resourcesactivities.dto.QuizzDTO;
import com.example.resourcesactivities.model.Course;
import com.example.resourcesactivities.model.Quizz;
import com.example.resourcesactivities.repository.QuizzRepository;
import com.example.resourcesactivities.service.QuizzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quizzes")
@CrossOrigin(originPatterns = "*")
public class QuizzController {
    @Autowired
    private QuizzRepository quizzRepository;
    @Autowired
    private QuizzService service;
    @GetMapping
    public ResponseEntity<List<QuizzDTO>> getAllQuizzes() {
        List<QuizzDTO> quizzDTOList= service.getAll();
        return new ResponseEntity<>(quizzDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quizz> getQuizzById(@PathVariable(value = "id") Integer quizzId) {
        Optional<Quizz> quizz = quizzRepository.findById(quizzId);
        return quizz.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Quizz> createQuizz(@RequestBody Quizz quizz) {
        Quizz savedQuizz = quizzRepository.save(quizz);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuizz);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizz(@PathVariable(value = "id") Integer quizzId) {
        Optional<Quizz> quizz = quizzRepository.findById(quizzId);
        if (quizz.isPresent()) {
            quizzRepository.delete(quizz.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
