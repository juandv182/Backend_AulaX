package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.CourseDTO;
import com.example.resourcesactivities.dto.ReinforceTopicDTO;
import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.model.Course;
import com.example.resourcesactivities.model.ReinforceTopic;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.CourseRepository;
import com.example.resourcesactivities.repository.ReinforceTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReinforceTopicService {
    @Autowired
    private ReinforceTopicRepository reinforceTopicRepository;
    @Autowired
    private CourseRepository courseRepository;

    public List<ReinforceTopicDTO> getTopicsByUserId(Long userId,Integer courseId) {
        List<ReinforceTopic> reinforceTopics = reinforceTopicRepository.findByUserId(userId);


        List<ReinforceTopicDTO> rtdto = reinforceTopics.stream().map(r->
        {
            CourseDTO cdto = CourseDTO.builder()
                    .id(r.getTopic().getCourse().getId())
                    .name(r.getTopic().getCourse().getName())
                    .description(r.getTopic().getCourse().getDescription())
                    .build();
            TopicDTO topic = TopicDTO.builder().id(r.getTopic().getId())
                    .name(r.getTopic().getName())
                    .createdAt(r.getTopic().getCreatedAt())
                    .competence(r.getTopic().getCompetence())
                    .course(cdto)
                    .description(r.getTopic().getDescription())
                    .updatedAt(r.getTopic().getUpdatedAt())
                    .build();
            return ReinforceTopicDTO.builder()
                    .id(r.getId())
                    .topic(topic)
                    .estado(r.getEstado())
                    .build();

        }).filter(r->r.getTopic().getCourse().getId()==courseId)
                .distinct().collect(Collectors.toList());

        return rtdto;
    }
}
