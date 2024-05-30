package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.MyResourceDTO;
import com.example.resourcesactivities.dto.ResourceFileDTO;
import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {
    @Autowired
    private TopicRepository repository;
    @Autowired
    private MyResourceRepository myResourceRepository;

    @Transactional
    public List<TopicDTO> getAllTopics(){
        List<Topic> topicList = repository.findAll();
        List<TopicDTO> topicDTOList = topicList.stream().map(c -> TopicDTO.builder()
                .id(c.getId())
                .name(c.getName())
                        .description((c.getDescription()))
                        .course((c.getCourse()))
                        .learningUnit(c.getLearningUnit())
                        .competence(c.getCompetence())
                        .status(c.getStatus())
                        .createdAt(c.getCreatedAt())
                        .updatedAt(c.getUpdatedAt())
                        .build()).collect(Collectors.toList());
        return topicDTOList;
    }
    @Transactional
    public TopicDTO getTopicById(Integer id) {
        Topic topic=repository.findById(id).
                orElseThrow(() -> new RuntimeException("Tema no encontrado con ID: " + id));

        return TopicDTO.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description((topic.getDescription()))
                .course((topic.getCourse()))
                .learningUnit(topic.getLearningUnit())
                .competence(topic.getCompetence())
                .status(topic.getStatus())
                .createdAt(topic.getCreatedAt())
                .updatedAt(topic.getUpdatedAt())
                .build();

    }
    @Transactional
    public List<MyResourceDTO> getResourcesByTopicId(Integer id) {
        return myResourceRepository.findByTopicId(id).stream().map(
                r->{
                    Topic topic=r.getTopic();
                    TopicDTO topicDTO = TopicDTO.builder()
                            .id(topic.getId())
                            .name(topic.getName())
                            .description((topic.getDescription()))
                            .course((topic.getCourse()))
                            .learningUnit(topic.getLearningUnit())
                            .competence(topic.getCompetence())
                            .status(topic.getStatus())
                            .createdAt(topic.getCreatedAt())
                            .updatedAt(topic.getUpdatedAt())
                            .build();
                    return MyResourceDTO.builder()
                            .id(r.getId())
                            .name(r.getName())
                            .description(r.getDescription())
                            .topic(topicDTO)
                            .status(r.getStatus())
                            .createdAt(r.getCreatedAt())
                            .updatedAt(r.getUpdatedAt())
                            .build();
                }).collect(Collectors.toList());
    }
    @Transactional
    public TopicDTO createTopic(TopicDTO topicDTO){
        Topic topic = new Topic();
        topic.setName(topicDTO.getName());
        topic.setDescription(topicDTO.getDescription());
        topic.setCourse(topicDTO.getCourse());
        topic.setLearningUnit(topicDTO.getLearningUnit());
        topic.setCompetence(topicDTO.getCompetence());
        topic.setStatus(topicDTO.getStatus());
        topic.setCreatedAt(topicDTO.getCreatedAt());
        topic.setUpdatedAt(topicDTO.getUpdatedAt());
        repository.save(topic);
        topicDTO.setId(topic.getId());
        return topicDTO;
    }
    @Transactional
    public boolean deleteTopic(Integer id) {
        Optional<Topic> topic = repository.findById(id);
        if (topic.isPresent()) {
            repository.delete(topic.get());
            return true;
        } else {
            return false;
        }
    }
}
