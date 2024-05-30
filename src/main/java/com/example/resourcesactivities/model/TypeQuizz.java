package com.example.resourcesactivities.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "typequizzes")
public class TypeQuizz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "typeQuizz",fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Quizz> quizzes = new HashSet<>();
}
