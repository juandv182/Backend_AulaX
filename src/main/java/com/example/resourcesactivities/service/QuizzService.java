package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.MyResourceDTO;
import com.example.resourcesactivities.dto.QuizzDTO;
import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.dto.TypeQuizzDTO;
import com.example.resourcesactivities.model.Quizz;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.QuizzRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                        //.topic(topicDTO)
                        .status(c.getMyResource().getStatus())
                        .createdAt(c.getMyResource().getCreatedAt())
                        .updatedAt(c.getMyResource().getUpdatedAt())
                        .build())
                .build()).collect(Collectors.toList());
        System.out.println(quizzDTOList);
        return quizzDTOList;
    }


}
