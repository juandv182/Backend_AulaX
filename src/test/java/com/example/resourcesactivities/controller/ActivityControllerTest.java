package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.Activity;
import com.example.resourcesactivities.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActivityControllerTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllActivities() {
        // Arrange
        List<Activity> activities = Arrays.asList(new Activity(), new Activity());
        when(activityRepository.findAll()).thenReturn(activities);

        // Act
        List<Activity> result = activityController.getAllActivities();

        // Assert
        assertEquals(activities.size(), result.size());
    }

    @Test
    void testGetActivityById_ActivityExists() {
        // Arrange
        int activityId = 1;
        Activity activity = new Activity();
        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));

        // Act
        ResponseEntity<Activity> response = activityController.getActivityById(activityId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activity, response.getBody());
    }

    @Test
    void testGetActivityById_ActivityNotExists() {
        // Arrange
        int activityId = 1;
        when(activityRepository.findById(activityId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Activity> response = activityController.getActivityById(activityId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateActivity() {
        // Arrange
        Activity activity = new Activity();
        when(activityRepository.save(activity)).thenReturn(activity);

        // Act
        ResponseEntity<Activity> response = activityController.createActivity(activity);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(activity, response.getBody());
    }

    @Test
    void testUpdateActivity_ActivityExists() {
        // Arrange
        int activityId = 1;
        Activity existingActivity = new Activity();
        Activity activityDetails = new Activity();
        when(activityRepository.findById(activityId)).thenReturn(Optional.of(existingActivity));
        when(activityRepository.save(existingActivity)).thenReturn(existingActivity);

        // Act
        ResponseEntity<Activity> response = activityController.updateActivity(activityId, activityDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingActivity, response.getBody());
    }

    @Test
    void testUpdateActivity_ActivityNotExists() {
        // Arrange
        int activityId = 1;
        Activity activityDetails = new Activity();
        when(activityRepository.findById(activityId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Activity> response = activityController.updateActivity(activityId, activityDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteActivity_ActivityExists() {
        // Arrange
        int activityId = 1;
        Activity activity = new Activity();
        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));

        // Act
        ResponseEntity<Void> response = activityController.deleteActivity(activityId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(activityRepository, times(1)).delete(activity);
    }

    @Test
    void testDeleteActivity_ActivityNotExists() {
        // Arrange
        int activityId = 1;
        when(activityRepository.findById(activityId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = activityController.deleteActivity(activityId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(activityRepository, never()).delete(any());
    }
}
