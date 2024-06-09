package com.example.resourcesactivities.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "type_files")
public class TypeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "typeFile",fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<ResourceFile> files;

}
