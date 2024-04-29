package com.example.resourcesactivities.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @ManyToOne
    @JoinColumn(name = "topic_id")
    @JsonBackReference
    private Topic topic;
    private Boolean status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "myResource",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ResourceFile> files =new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "myResource",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Activity> activities =new HashSet<>();

}
