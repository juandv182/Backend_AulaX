package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Competency;
import com.example.resourcesactivities.model.Course;
import com.example.resourcesactivities.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findAllByCompetence(Competency competence);
    List<Topic>  findAllByCourse(Course course);
}
