package com.example.resourcesactivities.repository;


import com.example.resourcesactivities.model.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizzRepository extends JpaRepository<Quizz, Integer> {
    List<Quizz> findByMyResourceId(Integer id);

}
