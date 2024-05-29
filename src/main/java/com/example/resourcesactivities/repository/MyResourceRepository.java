package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.MyResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyResourceRepository extends JpaRepository<MyResource, Integer> {
    MyResource findMyResourceById(Integer id);

    List<MyResource> findByTopicId(Integer topicId);


}

