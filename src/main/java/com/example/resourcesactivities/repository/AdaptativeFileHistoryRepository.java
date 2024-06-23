package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.AdaptativeFileHistory;
import com.example.resourcesactivities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdaptativeFileHistoryRepository extends JpaRepository<AdaptativeFileHistory, Long> {
    List<AdaptativeFileHistory> findByReinforceTopicUser(User user);
}
