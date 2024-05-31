package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Course;
import com.example.resourcesactivities.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByQuizzId(Integer id);
}
