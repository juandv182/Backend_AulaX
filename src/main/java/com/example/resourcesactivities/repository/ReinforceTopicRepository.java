package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.ReinforceTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReinforceTopicRepository extends JpaRepository<ReinforceTopic, Integer> {
    List<ReinforceTopic> findByUserId(Long userId);
}
