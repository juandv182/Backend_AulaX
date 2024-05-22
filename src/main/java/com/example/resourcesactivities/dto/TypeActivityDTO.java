package com.example.resourcesactivities.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypeActivityDTO {
    private Integer id;
    private String name;
    private String description;
    private Boolean status;
    private List<ActivityDTO> activities;
}
