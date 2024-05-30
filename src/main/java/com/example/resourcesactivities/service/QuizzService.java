package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.MyResourceDTO;
import com.example.resourcesactivities.dto.QuizzDTO;
import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.dto.TypeQuizzDTO;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.Quizz;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.model.TypeQuizz;
import com.example.resourcesactivities.repository.QuizzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizzService {
    @Autowired
    private QuizzRepository quizzRepository;
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
    public QuizzDTO save(QuizzDTO quizzDTO){
        Quizz quizz = new Quizz();
        quizz.setName(quizzDTO.getName());
        quizz.setNota(quizzDTO.getNota());
        MyResourceDTO rdto = quizzDTO.getMyResource();
        MyResource r = new MyResource();
        r.setId(rdto.getId());
        r.setDescription(rdto.getDescription());
        r.setName(rdto.getName());
        r.setStatus(rdto.getStatus());
        r.setCreatedAt(rdto.getCreatedAt());
        r.setUpdatedAt(rdto.getUpdatedAt());
        TopicDTO tdto=rdto.getTopic();
        Topic t=new Topic();
        t.setId(tdto.getId());
        r.setTopic(t);
        TypeQuizzDTO tqdto=quizzDTO.getTypeQuizz();
        TypeQuizz tq= new TypeQuizz();
        tq.setId(tqdto.getId());
        tq.setName(tqdto.getName());
        quizz.setMyResource(r);
        quizz.setTypeQuizz(tq);
        quizz.setCreatedAt(quizzDTO.getCreatedAt());
        quizzRepository.save(quizz);
        quizzDTO.setId(quizz.getId());
        return quizzDTO;
    }

}
