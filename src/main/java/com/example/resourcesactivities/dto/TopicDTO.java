package com.example.resourcesactivities.dto;

import com.example.resourcesactivities.model.Competency;
import com.example.resourcesactivities.model.Course;
import com.example.resourcesactivities.model.LearningUnit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicDTO {
    private Integer id;
    private String name;
    private String description;

    private Course course;

    private LearningUnit learningUnit;

    private Competency competence;
    private Boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<MyResourceDTO> resources;
}
