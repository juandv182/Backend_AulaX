package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.MyResourceDTO;
import com.example.resourcesactivities.dto.QuestionDTO;
import com.example.resourcesactivities.dto.QuizzDTO;
import com.example.resourcesactivities.dto.TypeQuizzDTO;
import com.example.resourcesactivities.model.Question;
import com.example.resourcesactivities.model.Quizz;
import com.example.resourcesactivities.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Transactional
    public List<QuestionDTO> getAll(){
        List<Question> questionList = questionRepository.findAll();
        List<QuestionDTO> questionDTOList = questionList.stream().map(c ->

                QuestionDTO.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .points((c.getPoints()))
                        .createdAt(c.getCreatedAt())
                        .updatedAt(c.getUpdatedAt())
                        .quizz(QuizzDTO.builder()
                                .id(c.getQuizz().getId())
                                .name(c.getQuizz().getName())
                                .nota((c.getQuizz().getNota()))
                                .createdAt(c.getQuizz().getCreatedAt())
                                .typeQuizz(TypeQuizzDTO.builder()
                                        .id(c.getQuizz().getTypeQuizz().getId())
                                        .name(c.getQuizz().getTypeQuizz().getName())
                                        .build())
                                .myResource(MyResourceDTO.builder()
                                        .id(c.getQuizz().getMyResource().getId())
                                        .build())
                                .build())
                        .build()).collect(Collectors.toList());
        return questionDTOList;
    }
    @Transactional
    public QuestionDTO getById(Integer id) {
        Question question=questionRepository.findById(id).
                orElseThrow(() -> new ExpressionException("Pregunta no encontrado con ID: " + id));

        return QuestionDTO.builder()
                .id(question.getId())
                .name(question.getName())
                .points((question.getPoints()))
                .correctAnswer(question.getCorrectAnswer())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .quizz(QuizzDTO.builder()
                        .id(question.getQuizz().getId())
                        .name(question.getQuizz().getName())
                        .nota((question.getQuizz().getNota()))
                        .createdAt(question.getQuizz().getCreatedAt())
                        .typeQuizz(TypeQuizzDTO.builder()
                                .id(question.getQuizz().getTypeQuizz().getId())
                                .name(question.getQuizz().getTypeQuizz().getName())
                                .build())
                        .myResource(MyResourceDTO.builder()
                                .id(question.getQuizz().getMyResource().getId())
                                .build())
                        .build())
                .build();

    }
    @Transactional
    public List<QuestionDTO> findByQuizzId(Integer id){
        return questionRepository.findByQuizzId(id).stream().map(
                question->{
                    return QuestionDTO.builder()
                            .id(question.getId())
                            .name(question.getName())
                            .correctAnswer(question.getCorrectAnswer())
                            .points((question.getPoints()))
                            .createdAt(question.getCreatedAt())
                            .updatedAt(question.getUpdatedAt())
                            .quizz(QuizzDTO.builder()
                                    .id(question.getQuizz().getId())
                                    .name(question.getQuizz().getName())
                                    .nota((question.getQuizz().getNota()))
                                    .createdAt(question.getQuizz().getCreatedAt())
                                    .typeQuizz(TypeQuizzDTO.builder()
                                            .id(question.getQuizz().getTypeQuizz().getId())
                                            .name(question.getQuizz().getTypeQuizz().getName())
                                            .build())
                                    .myResource(MyResourceDTO.builder()
                                            .id(question.getQuizz().getMyResource().getId())
                                            .build())
                                    .build())
                            .build();
                }).collect(Collectors.toList());

    }
    @Transactional
    public QuestionDTO save(QuestionDTO questionDTO){
        Question question = new Question();
        question.setName(questionDTO.getName());
        question.setPoints(questionDTO.getPoints());
        question.setCreatedAt(questionDTO.getCreatedAt());
        question.setUpdatedAt(questionDTO.getUpdatedAt());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        QuizzDTO qdto = questionDTO.getQuizz();
        Quizz q = new Quizz();
        q.setId(qdto.getId());
        question.setQuizz(q);
        questionRepository.save(question);
        questionDTO.setId(question.getId());
        return questionDTO;
    }
    @Transactional
    public void update(QuestionDTO questionDTO){
        questionRepository.findById(questionDTO.getId()).
                orElseThrow(() -> new RuntimeException("No se encontro el cuestionario a actualizar"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setName(questionDTO.getName());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setPoints(questionDTO.getPoints());
        question.setCreatedAt(questionDTO.getCreatedAt());
        question.setUpdatedAt(questionDTO.getUpdatedAt());
        QuizzDTO qdto = questionDTO.getQuizz();
        Quizz q = new Quizz();
        q.setId(qdto.getId());
        question.setQuizz(q);
        questionRepository.save(question);
    }
    @Transactional
    public void delete(Integer id){
        questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + id));
        questionRepository.deleteById(id);
    }
}
