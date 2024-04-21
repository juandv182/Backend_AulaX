package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Competency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompetencyRepository extends JpaRepository<Competency, Integer> {

}
