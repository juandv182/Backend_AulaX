package com.example.resourcesactivities.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivityDTO {
    private Integer id;
    private String name;
    private String description;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer myResourceId;
    private Integer typeActivityId;
}