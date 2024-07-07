package com.example.resourcesactivities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetencyProgressDTO {
    private String competencyName;
    private double progressPercentage;
}
