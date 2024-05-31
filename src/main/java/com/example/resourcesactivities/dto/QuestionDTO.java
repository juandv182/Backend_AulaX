package com.example.resourcesactivities.dto;

import com.example.resourcesactivities.model.Alternative;
import com.example.resourcesactivities.model.Quizz;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import software.amazon.ion.Decimal;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionDTO {
    private Integer id;
    private String name;
    private QuizzDTO quizz;
    private Double points;
    private String correctAnswer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AlternativeDTO> alternatives;
}
