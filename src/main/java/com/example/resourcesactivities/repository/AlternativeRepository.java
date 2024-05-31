package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlternativeRepository extends JpaRepository<Alternative, Integer> {
    List<Alternative> findByQuestionId(Integer id);
}
