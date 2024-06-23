package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.ReinforceTopicDTO;
import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.model.ReinforceTopic;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.ReinforceTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReinforceTopicService {
    @Autowired
    private ReinforceTopicRepository reinforceTopicRepository;

    public List<ReinforceTopicDTO> getTopicsByUserId(Long userId,Integer courseId) {
        List<ReinforceTopic> reinforceTopics = reinforceTopicRepository.findByUserId(userId);


        List<ReinforceTopicDTO> rtdto = reinforceTopics.stream().map(r->
        {
            TopicDTO topic = TopicDTO.builder().id(r.getTopic().getId())
                    .name(r.getTopic().getName())
                    .createdAt(r.getTopic().getCreatedAt())
                    .competence(r.getTopic().getCompetence())
                    .course(r.getTopic().getCourse())
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
