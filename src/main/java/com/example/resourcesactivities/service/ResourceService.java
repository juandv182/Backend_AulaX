package com.example.resourcesactivities.service;

import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.Competency;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.CompetencyRepository;
import com.example.resourcesactivities.repository.MyResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    @Autowired
    private MyResourceRepository myResourceRepository;

    @Autowired
    private CompetencyRepository competencyRepository;

    public List<MyResource> searchResources(Integer topicId, List<Integer> competencies, List<Integer> courses) {
        List<MyResource> myResources;
        if (topicId != 0) {
            myResources = myResourceRepository.findByTopicId(topicId);
        } else {
            myResources = myResourceRepository.findAll();
        }

        if (competencies != null && !competencies.isEmpty()) {
            myResources = myResources.stream()
                    .filter(resource -> competencies.contains(resource.getTopic().getCompetence().getId()))
                    .collect(Collectors.toList());
        }
        if (courses != null && !courses.isEmpty()) {
            myResources = myResources.stream()
                    .filter(resource -> courses.contains(resource.getTopic().getCourse().getId()))
                    .collect(Collectors.toList());
        }

        return myResources;
    }
}
