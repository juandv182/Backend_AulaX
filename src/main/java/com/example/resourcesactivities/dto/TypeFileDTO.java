package com.example.resourcesactivities.dto;

import com.example.resourcesactivities.model.ResourceFile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeFileDTO {
    private Integer id;
    private String name;
    private Set<ResourceFileDTO> files;
}
