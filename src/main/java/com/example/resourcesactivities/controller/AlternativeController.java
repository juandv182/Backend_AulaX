package com.example.resourcesactivities.controller;


import com.example.resourcesactivities.dto.AlternativeDTO;
import com.example.resourcesactivities.model.Alternative;
import com.example.resourcesactivities.repository.AlternativeRepository;
import com.example.resourcesactivities.service.AlternativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alternatives")
@CrossOrigin(originPatterns = "*")
public class AlternativeController {
    @Autowired
    private AlternativeService alternativeService;
    @GetMapping
    public ResponseEntity<List<AlternativeDTO>> getAllAlternatives() {

        List<AlternativeDTO> dtoList= alternativeService.getAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlternativeDTO> getAlternativeById(@PathVariable(value = "id") Integer alternativeId) {
        AlternativeDTO alternative = alternativeService.getById(alternativeId);
        return alternative != null ? ResponseEntity.ok().body(alternative) : ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}/QuestionId")
    public ResponseEntity<List<AlternativeDTO>> getQuizzByMyResourceId(@PathVariable(value = "id") Integer questionId) {
        List<AlternativeDTO> DTOlist=alternativeService.findByQuestionId(questionId);
        return new ResponseEntity<>(DTOlist, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<AlternativeDTO> createAlternative(@RequestBody AlternativeDTO alternative) {
        AlternativeDTO adto = alternativeService.save(alternative);
        return ResponseEntity.status(HttpStatus.CREATED).body(adto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAlternative(@RequestBody AlternativeDTO adto, @PathVariable Integer id) {
        adto.setId(id);
        alternativeService.update(adto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlternative(@PathVariable Integer id) {
        alternativeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
