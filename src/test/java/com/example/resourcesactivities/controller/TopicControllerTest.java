package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.TopicRepository;
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

class TopicControllerTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicController topicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTopics() {
        // Arrange
        List<Topic> topics = Arrays.asList(new Topic(), new Topic());
        when(topicRepository.findAll()).thenReturn(topics);

        // Act
        List<Topic> result = topicController.getAllTopics();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetTopicById_TopicExists() {
        // Arrange
        Integer topicId = 1;
        Topic topic = new Topic();
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        // Act
        ResponseEntity<Topic> response = topicController.getTopicById(topicId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetTopicById_TopicDoesNotExist() {
        // Arrange
        Integer topicId = 1;
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Topic> response = topicController.getTopicById(topicId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateTopic() {
        // Arrange
        Topic topic = new Topic();

        // Act
        ResponseEntity<Topic> response = topicController.createTopic(topic);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    void testUpdateTopic_TopicExists() {
        // Arrange
        Integer topicId = 1;
        Topic topicDetails = new Topic();
        Topic existingTopic = new Topic();
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(existingTopic));

        // Act
        ResponseEntity<Topic> response = topicController.updateTopic(topicId, topicDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateTopic_TopicDoesNotExist() {
        // Arrange
        Integer topicId = 1;
        Topic topicDetails = new Topic();
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Topic> response = topicController.updateTopic(topicId, topicDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteTopic_TopicExists() {
        // Arrange
        Integer topicId = 1;
        Topic existingTopic = new Topic();
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(existingTopic));

        // Act
        ResponseEntity<Void> response = topicController.deleteTopic(topicId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteTopic_TopicDoesNotExist() {
        // Arrange
        Integer topicId = 1;
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = topicController.deleteTopic(topicId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
