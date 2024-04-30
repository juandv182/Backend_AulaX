package com.example.resourcesactivities.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "files")
public class ResourceFile {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
    private String folder;
    private String url;
    @ManyToOne
    @JoinColumn(name = "resource_id")
    @JsonBackReference
    private MyResource myResource;
    private Boolean status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
