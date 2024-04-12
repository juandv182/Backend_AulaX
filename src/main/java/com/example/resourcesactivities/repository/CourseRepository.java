package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
