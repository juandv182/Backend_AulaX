package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.TypeFile;
import com.example.resourcesactivities.model.TypeQuizz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeQuizzRepository extends JpaRepository<TypeQuizz, Integer> {
}
