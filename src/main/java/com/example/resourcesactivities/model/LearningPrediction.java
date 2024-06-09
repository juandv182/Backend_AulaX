package com.example.resourcesactivities.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "learning_predictions")
public class LearningPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer age;
    private Integer gender;
    private String learn;
    private Integer q1;
    private Integer q2;
    private Integer q3;
    private Integer q4;
    private Integer q5;
    private Integer q6;
    private Integer q7;
    private Integer q8;
    private Integer q9;
    private Integer q10;
    private Integer q11;
    private Integer q12;
    private Integer q13;
    private Integer q14;
    private Integer q15;
    @ManyToOne
    @JoinColumn(name = "quizz_id")
    private Quizz quizz;


}
