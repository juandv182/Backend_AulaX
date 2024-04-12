package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
}
