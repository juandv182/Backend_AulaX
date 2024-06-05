package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.*;
import com.example.resourcesactivities.model.*;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.repository.QuestionRepository;
import com.example.resourcesactivities.repository.QuizzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizzService {
    @Autowired
    private QuizzRepository quizzRepository;
    @Autowired
    private MyResourceRepository myResourceRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Transactional
    public List<QuizzDTO> getAll(){
        List<Quizz> quizzList = quizzRepository.findAll();
        List<QuizzDTO> quizzDTOList = quizzList.stream().map(c ->

                 QuizzDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .nota((c.getNota()))
                .createdAt(c.getCreatedAt())
                .typeQuizz(TypeQuizzDTO.builder()
                        .id(c.getTypeQuizz().getId())
                        .name(c.getTypeQuizz().getName())
                        .build())
                .myResource(MyResourceDTO.builder()
                        .id(c.getMyResource().getId())
                        .name(c.getMyResource().getName())
                        .description(c.getMyResource().getDescription())
                        .topic(TopicDTO.builder()
                                .id(c.getMyResource().getTopic().getId())
                                .build())
                        .status(c.getMyResource().getStatus())
                        .createdAt(c.getMyResource().getCreatedAt())
                        .updatedAt(c.getMyResource().getUpdatedAt())
                        .build())
                .build()).collect(Collectors.toList());
        return quizzDTOList;
    }
    @Transactional
    public QuizzDTO getById(Integer id) {
        Quizz quizz=quizzRepository.findById(id).
                orElseThrow(() -> new ExpressionException("Cuestionario no encontrado con ID: " + id));

        return QuizzDTO.builder()
                .id(quizz.getId())
                .name(quizz.getName())
                .nota((quizz.getNota()))
                .createdAt(quizz.getCreatedAt())
                .typeQuizz(TypeQuizzDTO.builder()
                        .id(quizz.getTypeQuizz().getId())
                        .name(quizz.getTypeQuizz().getName())
                        .build())
                .myResource(MyResourceDTO.builder()
                        .id(quizz.getMyResource().getId())
                        .name(quizz.getMyResource().getName())
                        .description(quizz.getMyResource().getDescription())
                        .topic(TopicDTO.builder()
                                .id(quizz.getMyResource().getTopic().getId())
                                .build())
                        .status(quizz.getMyResource().getStatus())
                        .createdAt(quizz.getMyResource().getCreatedAt())
                        .updatedAt(quizz.getMyResource().getUpdatedAt())
                        .build())
                .build();

    }
    @Transactional
    public List<QuizzDTO> findByMyResourceId(Integer id){
    return quizzRepository.findByMyResourceId(id).stream().map(
            quizz->{
                return QuizzDTO.builder()
                        .id(quizz.getId())
                        .name(quizz.getName())
                        .nota((quizz.getNota()))
                        .createdAt(quizz.getCreatedAt())
                        .typeQuizz(TypeQuizzDTO.builder()
                                .id(quizz.getTypeQuizz().getId())
                                .name(quizz.getTypeQuizz().getName())
                                .build())
                        .myResource(MyResourceDTO.builder()
                                .id(quizz.getMyResource().getId())
                                .name(quizz.getMyResource().getName())
                                .description(quizz.getMyResource().getDescription())
                                .topic(TopicDTO.builder()
                                        .id(quizz.getMyResource().getTopic().getId())
                                        .build())
                                .status(quizz.getMyResource().getStatus())
                                .createdAt(quizz.getMyResource().getCreatedAt())
                                .updatedAt(quizz.getMyResource().getUpdatedAt())
                                .build())
                        .build();
            }).collect(Collectors.toList());

    }
    @Transactional
    public QuizzDTO save(QuizzDTO quizzDTO){

        Quizz quizz = new Quizz();
        quizz.setName(quizzDTO.getName());

        MyResource r = new MyResource();
        r.setId(quizzDTO.getMyResource().getId());
        TypeQuizzDTO tqdto=quizzDTO.getTypeQuizz();
        TypeQuizz tq= new TypeQuizz();
        tq.setId(tqdto.getId());
        if(tqdto.getId()==3){

        }
        quizz.setMyResource(r);
        quizz.setTypeQuizz(tq);
        quizz.setCreatedAt(quizzDTO.getCreatedAt());
        quizz.setNota(quizzDTO.getNota());
        quizzRepository.save(quizz);
        quizzDTO.setId(quizz.getId());
        return quizzDTO;
    }
    @Transactional
    public void update(QuizzDTO quizzDTO){
        quizzRepository.findById(quizzDTO.getId()).
                orElseThrow(() -> new RuntimeException("No se encontro el cuestionario a actualizar"));
        Quizz quizz = new Quizz();
        quizz.setId(quizzDTO.getId());
        quizz.setName(quizzDTO.getName());
        quizz.setNota(quizzDTO.getNota());
        MyResourceDTO rdto = quizzDTO.getMyResource();
        MyResource r = new MyResource();
        r.setId(rdto.getId());
        TypeQuizzDTO tqdto=quizzDTO.getTypeQuizz();
        TypeQuizz tq= new TypeQuizz();
        tq.setId(tqdto.getId());
        quizz.setMyResource(r);
        quizz.setTypeQuizz(tq);
        quizz.setCreatedAt(quizzDTO.getCreatedAt());
        quizzRepository.save(quizz);
    }
    @Transactional
    public void delete(Integer id){
        quizzRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + id));
        quizzRepository.deleteById(id);
    }
    @Transactional
    public void updateNota(Integer quizzId) {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));
        quizz.updateNota();
        quizzRepository.save(quizz);
    }

    public Map<String, Map<String, List<QuestionDTO>>> getQuestionsGroupedByCompetencyAndLearningUnit(Integer quizzId) {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));

        if (!"curso".equals(quizz.getTypeQuizz().getName())) {
            throw new RuntimeException("Este método solo es aplicable para cuestionarios de tipo 'PorCurso'");
        }

        List<Question> questions = questionRepository.findByQuizzId(quizzId);

        Map<String, Map<String, List<QuestionDTO>>> groupedQuestions = new HashMap<>();

        for (Question question : questions) {
            String competencyName = question.getQuizz().getMyResource().getTopic().getCompetence().getName();
            String learningUnitName = question.getQuizz().getMyResource().getTopic().getLearningUnit().getName();

            groupedQuestions
                    .computeIfAbsent(competencyName, k -> new HashMap<>())
                    .computeIfAbsent(learningUnitName, k -> new ArrayList<>())
                    .add(convertToDto(question));
        }

        return groupedQuestions;
    }

    private QuestionDTO convertToDto(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .name(question.getName())
                .points(question.getPoints())
                .correctAnswer(question.getCorrectAnswer())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .alternatives(question.getAlternatives().stream().map(this::convertToDto).collect(Collectors.toList()))
                .build();
    }

    private AlternativeDTO convertToDto(Alternative alternative) {
        return AlternativeDTO.builder()
                .id(alternative.getId())
                .value(alternative.getValue())
                .is_answer(alternative.getIs_answer())
                .is_marked(alternative.getIs_marked())
                .build();
    }


}
