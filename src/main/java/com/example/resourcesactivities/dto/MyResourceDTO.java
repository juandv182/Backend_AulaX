package com.example.resourcesactivities.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyResourceDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer topicId;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<ResourceFileDTO> files;
    private Set<ActivityDTO> activities;
}
