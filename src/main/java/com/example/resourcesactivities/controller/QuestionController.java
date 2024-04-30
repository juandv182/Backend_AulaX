package com.example.resourcesactivities.controller;



import com.example.resourcesactivities.model.Question;
import com.example.resourcesactivities.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
@CrossOrigin(originPatterns = "*")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable(value = "id") Integer questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        return question.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        Question savedQuestion = questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable(value = "id") Integer questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()) {
            questionRepository.delete(question.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
