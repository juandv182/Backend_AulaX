package com.example.resourcesactivities.controller;



import com.example.resourcesactivities.dto.QuestionDTO;
import com.example.resourcesactivities.model.Question;
import com.example.resourcesactivities.repository.QuestionRepository;
import com.example.resourcesactivities.service.QuestionService;
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
    private QuestionService service;
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questionDTOList= service.getAll();
        return new ResponseEntity<>(questionDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Integer id) {
        QuestionDTO question = service.getById(id);
        return question != null ? ResponseEntity.ok().body(question) : ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}/Quizz")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByQuizzId(@PathVariable Integer id) {
        List<QuestionDTO> questionDTOList=service.findByQuizzId(id);
        return new ResponseEntity<>(questionDTOList, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO question) {
        QuestionDTO qdto = service.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(qdto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable(value = "id") Integer questionId) {
        service.delete(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
