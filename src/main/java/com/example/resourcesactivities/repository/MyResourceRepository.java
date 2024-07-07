package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.MyResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MyResourceRepository extends JpaRepository<MyResource, Integer> {
    MyResource findMyResourceById(Integer id);

    List<MyResource> findByTopicId(Integer topicId);
    @Query("SELECT mr FROM MyResource mr " +
            "JOIN mr.topic t " +
            "JOIN t.course c " +
            "JOIN c.users u " +
            "WHERE c.id = :courseId AND u.id = :userId AND mr.mode='library'")
    List<MyResource> findByCourseIdAndUserId(@Param("courseId") Integer courseId, @Param("userId") Long userId);


}

