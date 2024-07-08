package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAll(); // Método para obtener todos los cursos
}
