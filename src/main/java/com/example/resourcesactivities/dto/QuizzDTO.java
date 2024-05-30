package com.example.resourcesactivities.dto;

import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.Question;
import com.example.resourcesactivities.model.TypeQuizz;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizzDTO {
    private Integer id;
    private String name;
    private Double nota;
    private LocalDateTime createdAt;
    private MyResourceDTO myResource;
    private TypeQuizzDTO typeQuizz;
    private List<Question> questions;
}
