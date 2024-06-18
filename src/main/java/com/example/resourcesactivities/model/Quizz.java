package com.example.resourcesactivities.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import software.amazon.ion.Decimal;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private MyResource myResource;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typequizz_id")
    private TypeQuizz typeQuizz;
    @OneToMany(mappedBy = "quizz",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Question> questions =new HashSet<>();
    public void updateNota() {
        System.out.println("Accediendo a updateNota");
        Double n=0.0;
        for(Question question:this.questions){

            for(Alternative al:question.getAlternatives()){
                if(al.getIs_marked() && al.getIs_answer()){
                    n+=question.getPoints();
                }
            }
        }
        this.nota +=  n;
        System.out.println("Nota actualizada: " + this.nota );
    }
}