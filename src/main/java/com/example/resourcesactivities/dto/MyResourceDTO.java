package com.example.resourcesactivities.dto;

import com.example.resourcesactivities.model.Topic;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyResourceDTO {
    private Integer id;
    private String name;
    private String description;
    private TopicDTO topic;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<ResourceFileDTO> files;
    private Set<QuizzDTO> quizzes;

}
