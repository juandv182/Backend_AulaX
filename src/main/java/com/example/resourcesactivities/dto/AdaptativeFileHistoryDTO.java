package com.example.resourcesactivities.dto;

import com.example.resourcesactivities.model.ReinforceTopic;
import com.example.resourcesactivities.model.ResourceFile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdaptativeFileHistoryDTO {

    private Long id;

    private ReinforceTopic reinforceTopic;

    private ResourceFile file;

    private Double nota;

    private LocalDateTime viewedAt;
}
