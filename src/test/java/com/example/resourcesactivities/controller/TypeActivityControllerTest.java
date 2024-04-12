package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.TypeActivity;
import com.example.resourcesactivities.repository.TypeActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TypeActivityControllerTest {

    @Mock
    private TypeActivityRepository typeActivityRepository;

    @InjectMocks
    private TypeActivityController typeActivityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTypeActivities() {
        // Arrange
        List<TypeActivity> typeActivities = Arrays.asList(new TypeActivity(), new TypeActivity());
        when(typeActivityRepository.findAll()).thenReturn(typeActivities);

        // Act
        List<TypeActivity> result = typeActivityController.getAllTypeActivities();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetTypeActivityById_TypeActivityExists() {
        // Arrange
        Integer typeActivityId = 1;
        TypeActivity typeActivity = new TypeActivity();
        when(typeActivityRepository.findById(typeActivityId)).thenReturn(Optional.of(typeActivity));

        // Act
        ResponseEntity<TypeActivity> response = typeActivityController.getTypeActivityById(typeActivityId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetTypeActivityById_TypeActivityDoesNotExist() {
        // Arrange
        Integer typeActivityId = 1;
        when(typeActivityRepository.findById(typeActivityId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<TypeActivity> response = typeActivityController.getTypeActivityById(typeActivityId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateTypeActivity() {
        // Arrange
        TypeActivity typeActivity = new TypeActivity();

        // Act
        ResponseEntity<TypeActivity> response = typeActivityController.createTypeActivity(typeActivity);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    void testUpdateTypeActivity_TypeActivityExists() {
        // Arrange
        Integer typeActivityId = 1;
        TypeActivity typeActivityDetails = new TypeActivity();
        TypeActivity existingTypeActivity = new TypeActivity();
        when(typeActivityRepository.findById(typeActivityId)).thenReturn(Optional.of(existingTypeActivity));

        // Act
        ResponseEntity<TypeActivity> response = typeActivityController.updateTypeActivity(typeActivityId, typeActivityDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateTypeActivity_TypeActivityDoesNotExist() {
        // Arrange
        Integer typeActivityId = 1;
        TypeActivity typeActivityDetails = new TypeActivity();
        when(typeActivityRepository.findById(typeActivityId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<TypeActivity> response = typeActivityController.updateTypeActivity(typeActivityId, typeActivityDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteTypeActivity_TypeActivityExists() {
        // Arrange
        Integer typeActivityId = 1;
        TypeActivity existingTypeActivity = new TypeActivity();
        when(typeActivityRepository.findById(typeActivityId)).thenReturn(Optional.of(existingTypeActivity));

        // Act
        ResponseEntity<Void> response = typeActivityController.deleteTypeActivity(typeActivityId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteTypeActivity_TypeActivityDoesNotExist() {
        // Arrange
        Integer typeActivityId = 1;
        when(typeActivityRepository.findById(typeActivityId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = typeActivityController.deleteTypeActivity(typeActivityId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
