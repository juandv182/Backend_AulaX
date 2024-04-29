package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.Activity;
import com.example.resourcesactivities.model.MyResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findByTypeActivityId(Integer typeActivityId);
    List<MyResource> findByResourceId(Integer resourceId);
}
