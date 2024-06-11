package com.example.resourcesactivities.controller;



import com.example.resourcesactivities.dto.AlternativeDTO;
import com.example.resourcesactivities.dto.QuestionDTO;
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
import java.util.Map;
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
    @GetMapping("/{id}/TypeQuizzId")
    public ResponseEntity<List<QuizzDTO>> getQuizzByTypeQuizzId(@PathVariable(value = "id") Integer id) {
        List<QuizzDTO> quizzDTOList=service.findByTypeQuizzId(id);
        return new ResponseEntity<>(quizzDTOList, HttpStatus.OK);
    }
    @GetMapping("/{id}/grouped-questions")
    public ResponseEntity<Map<String, Map<String, Map<String, List<QuestionDTO>>>>> getQuestionsGroupedByCompetencyAndLearningUnit(@PathVariable Integer id) {
        try {
            Map<String, Map<String, Map<String, List<QuestionDTO>>>> groupedQuestions = service.getQuestionsGroupedByCompetencyAndLearningUnit(id);
            return new ResponseEntity<>(groupedQuestions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{quizzId}/course/{courseId}/course-grouped-questions")
    public ResponseEntity<Map<String, Map<String, Map<String, List<QuestionDTO>>>>> getQuestionsForCourseGroupedByCompetencyAndLearningUnit(@PathVariable Integer quizzId, @PathVariable Integer courseId) {
        try {
            Map<String, Map<String, Map<String, List<QuestionDTO>>>>  groupedQuestions = service.getQuestionsForCourseGroupedByCompetencyAndLearningUnit(quizzId,courseId);
            return new ResponseEntity<>(groupedQuestions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{quizzId}/course/{courseId}/detailed-total-nota")
    public ResponseEntity<Map<String, Object>> getDetailedTotalNotaForCourse(@PathVariable Integer quizzId, @PathVariable Integer courseId) {
        try {
            Map<String, Object> result = service.getTotalNotaForCourse(quizzId, courseId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}/marked-alternatives")
    public ResponseEntity<List<AlternativeDTO>> getMarkedAlternativesForQuizz(@PathVariable Integer id) {
        try {
            List<AlternativeDTO> markedAlternatives = service.getMarkedAlternativesForQuizz(id);
            return new ResponseEntity<>(markedAlternatives, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsWithAlternativesForQuizz(@PathVariable Integer id) {
        try {
            List<QuestionDTO> questions = service.getQuestionsWithAlternativesForQuizz(id);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
