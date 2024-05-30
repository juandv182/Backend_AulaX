package com.example.resourcesactivities.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceFileDTO {

    private UUID id;
    private String name;
    private String url;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MyResourceDTO resource;
    private TypeFileDTO typeFile;

    public ResourceFileDTO(UUID id, String name, String url, Boolean status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id=id;
        this.name=name;
        this.url=url;
        this.status=status;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;


    }
}
