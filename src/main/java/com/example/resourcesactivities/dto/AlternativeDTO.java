package com.example.resourcesactivities.dto;

import com.example.resourcesactivities.model.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlternativeDTO {
    private Integer id;
    private String value;
    private QuestionDTO question;
    private Boolean is_answer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
