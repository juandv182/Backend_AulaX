package com.example.resourcesactivities.repository;


import com.example.resourcesactivities.model.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizzRepository extends JpaRepository<Quizz, Integer> {
}
