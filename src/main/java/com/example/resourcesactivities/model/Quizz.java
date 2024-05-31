package com.example.resourcesactivities.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import software.amazon.ion.Decimal;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "quizzes")
public class Quizz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double nota;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id")
    private MyResource myResource;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typequizz_id")
    private TypeQuizz typeQuizz;
    @OneToMany(mappedBy = "quizz",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Question> questions =new HashSet<>();
    public void updateNota() {
        this.nota = this.questions.stream()
                .filter(question -> question.getAlternatives().stream()
                        .allMatch(alternative -> alternative.getIs_answer() && alternative.getIs_marked()))
                .mapToDouble(Question::getPoints)
                .sum();
    }
}