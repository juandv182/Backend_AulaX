package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
