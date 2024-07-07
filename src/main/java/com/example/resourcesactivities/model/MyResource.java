package com.example.resourcesactivities.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "resources")
public class MyResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    //private String file;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    private Topic topic;
    private String mode; // "library" o "adaptive"
    private Boolean status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "myResource",fetch=FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResourceFile> files =new HashSet<>();
    @OneToMany(mappedBy = "myResource",fetch=FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Quizz> quizzes =new HashSet<>();


}