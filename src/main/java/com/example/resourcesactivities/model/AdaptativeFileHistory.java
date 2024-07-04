package com.example.resourcesactivities.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adaptative_file_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdaptativeFileHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reinforce_topic_id", nullable = false)
    private ReinforceTopic reinforceTopic;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private ResourceFile file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizz_id", nullable = false)
    private Quizz quizzDado;



    private LocalDateTime viewedAt;
}
