package com.example.resourcesactivities.dto;

import com.example.resourcesactivities.model.Quizz;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeQuizzDTO {
    private Integer id;
    private String name;
    private List<QuizzDTO> quizzes;
}
