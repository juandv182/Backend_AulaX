package com.example.resourcesactivities.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResourceFileDTO {

    private UUID id;
    private String name;
    private String url;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
