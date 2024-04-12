package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Competency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetencyRepository extends JpaRepository<Competency, Integer> {
}
