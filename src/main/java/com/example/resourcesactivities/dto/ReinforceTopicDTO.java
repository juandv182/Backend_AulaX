package com.example.resourcesactivities.dto;

import com.example.resourcesactivities.model.Quizz;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReinforceTopicDTO {

    private Integer id;
    private UserDTO user;
    private TopicDTO topic;
    private QuizzDTO quizz;
    private LocalDateTime createdAt;
}
