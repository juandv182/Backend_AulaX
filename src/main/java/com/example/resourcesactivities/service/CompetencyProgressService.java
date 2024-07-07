package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.CompetencyProgressDTO;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.repository.CourseRepository;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetencyProgressService {

    @Autowired
    private MyResourceRepository myResourceRepository;

    public List<CompetencyProgressDTO> getProgressByCompetency(Integer courseId, Long userId) {
        List<MyResource> resources = myResourceRepository.findByCourseIdAndUserId(courseId, userId);
        System.out.println(resources.size());
        return resources.stream()
                .collect(Collectors.groupingBy(resource -> resource.getTopic().getCompetence().getName(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new CompetencyProgressDTO(entry.getKey(), calculatePercentage(entry.getValue(), resources.size())))
                .collect(Collectors.toList());
    }

    private double calculatePercentage(long part, long total) {
        return (double) part / total * 100;
    }
}