package com.example.resourcesactivities.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryFileDTO {
    private Integer id;
    private ResourceFileDTO file;
    private LocalDateTime clickedAt;
}
