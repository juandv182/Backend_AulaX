package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.TypeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeFileRepository extends JpaRepository<TypeFile, Integer> {
}
