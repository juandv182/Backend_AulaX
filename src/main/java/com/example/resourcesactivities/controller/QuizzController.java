package com.example.resourcesactivities.controller;



import com.example.resourcesactivities.dto.QuizzDTO;
import com.example.resourcesactivities.dto.TopicDTO;
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
    public ResponseEntity<QuizzDTO> getQuizzById(@PathVariable(value = "id") Integer quizzId) {
        QuizzDTO quizz = service.getById(quizzId);
        return quizz != null ? ResponseEntity.ok().body(quizz) : ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}/MyResourceId")
    public ResponseEntity<List<QuizzDTO>> getQuizzByMyResourceId(@PathVariable(value = "id") Integer myresourceId) {
        List<QuizzDTO> quizzDTOList=service.findByMyResourceId(myresourceId);
        return new ResponseEntity<>(quizzDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<QuizzDTO> createQuizz(@RequestBody QuizzDTO quizzdto) {
        QuizzDTO qdto = service.save(quizzdto);
        return ResponseEntity.status(HttpStatus.CREATED).body(qdto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateQuizz(@RequestBody QuizzDTO quizzdto, @PathVariable Integer id) {
        quizzdto.setId(id);
        service.update(quizzdto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizz(@PathVariable(value = "id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
