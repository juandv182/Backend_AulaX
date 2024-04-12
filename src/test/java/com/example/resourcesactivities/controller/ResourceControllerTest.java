package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.service.ResourceService;
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

class ResourceControllerTest {

    @Mock
    private MyResourceRepository myResourceRepository;

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllResources() {
        // Arrange
        List<MyResource> resources = Arrays.asList(new MyResource(), new MyResource());
        when(myResourceRepository.findAll()).thenReturn(resources);

        // Act
        List<MyResource> result = resourceController.getAllResources();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetResourceById_ResourceExists() {
        // Arrange
        Integer resourceId = 1;
        MyResource resource = new MyResource();
        when(myResourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        // Act
        ResponseEntity<MyResource> response = resourceController.getResourceById(resourceId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetResourceById_ResourceDoesNotExist() {
        // Arrange
        Integer resourceId = 1;
        when(myResourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<MyResource> response = resourceController.getResourceById(resourceId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateResource() {
        // Arrange
        MyResource myResource = new MyResource();

        // Act
        ResponseEntity<MyResource> response = resourceController.createResource(myResource);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    void testUpdateResource_ResourceExists() {
        // Arrange
        Integer resourceId = 1;
        MyResource myResourceDetails = new MyResource();
        MyResource existingMyResource = new MyResource();
        when(myResourceRepository.findById(resourceId)).thenReturn(Optional.of(existingMyResource));

        // Act
        ResponseEntity<MyResource> response = resourceController.updateResource(resourceId, myResourceDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateResource_ResourceDoesNotExist() {
        // Arrange
        Integer resourceId = 1;
        MyResource myResourceDetails = new MyResource();
        when(myResourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<MyResource> response = resourceController.updateResource(resourceId, myResourceDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteResource_ResourceExists() {
        // Arrange
        Integer resourceId = 1;
        MyResource existingMyResource = new MyResource();
        when(myResourceRepository.findById(resourceId)).thenReturn(Optional.of(existingMyResource));

        // Act
        ResponseEntity<Void> response = resourceController.deleteResource(resourceId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteResource_ResourceDoesNotExist() {
        // Arrange
        Integer resourceId = 1;
        when(myResourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = resourceController.deleteResource(resourceId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSearchResources_ResourcesFound() {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("topic_id", 1);
        requestBody.put("competencies", Arrays.asList(1, 2));
        requestBody.put("courses", Arrays.asList(3, 4));
        List<MyResource> resources = Arrays.asList(new MyResource(), new MyResource());
        when(resourceService.searchResources(1, Arrays.asList(1, 2), Arrays.asList(3, 4))).thenReturn(resources);

        // Act
        ResponseEntity<List<MyResource>> response = resourceController.searchResources(requestBody);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testSearchResources_ResourcesNotFound() {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("topic_id", 1);
        requestBody.put("competencies", Arrays.asList(1, 2));
        requestBody.put("courses", Arrays.asList(3, 4));
        when(resourceService.searchResources(1, Arrays.asList(1, 2), Arrays.asList(3, 4))).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<MyResource>> response = resourceController.searchResources(requestBody);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
