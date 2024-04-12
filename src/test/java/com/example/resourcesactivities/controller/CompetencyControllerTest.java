package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.Competency;
import com.example.resourcesactivities.repository.CompetencyRepository;
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

class CompetencyControllerTest {

    @Mock
    private CompetencyRepository competencyRepository;

    @InjectMocks
    private CompetencyController competencyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCompetencies() {
        // Arrange
        List<Competency> competencies = Arrays.asList(new Competency(), new Competency());
        when(competencyRepository.findAll()).thenReturn(competencies);

        // Act
        List<Competency> result = competencyController.getAllCompetencies();

        // Assert
        assertEquals(competencies.size(), result.size());
    }

    @Test
    void testGetCompetencyById_CompetencyExists() {
        // Arrange
        int competencyId = 1;
        Competency competency = new Competency();
        when(competencyRepository.findById(competencyId)).thenReturn(Optional.of(competency));

        // Act
        ResponseEntity<Competency> response = competencyController.getCompetencyById(competencyId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(competency, response.getBody());
    }

    @Test
    void testGetCompetencyById_CompetencyNotExists() {
        // Arrange
        int competencyId = 1;
        when(competencyRepository.findById(competencyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Competency> response = competencyController.getCompetencyById(competencyId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateCompetency() {
        // Arrange
        Competency competency = new Competency();
        when(competencyRepository.save(competency)).thenReturn(competency);

        // Act
        ResponseEntity<Competency> response = competencyController.createCompetency(competency);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(competency, response.getBody());
    }

    @Test
    void testUpdateCompetency_CompetencyExists() {
        // Arrange
        int competencyId = 1;
        Competency existingCompetency = new Competency();
        Competency competencyDetails = new Competency();
        when(competencyRepository.findById(competencyId)).thenReturn(Optional.of(existingCompetency));
        when(competencyRepository.save(existingCompetency)).thenReturn(existingCompetency);

        // Act
        ResponseEntity<Competency> response = competencyController.updateCompetency(competencyId, competencyDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingCompetency, response.getBody());
    }

    @Test
    void testUpdateCompetency_CompetencyNotExists() {
        // Arrange
        int competencyId = 1;
        Competency competencyDetails = new Competency();
        when(competencyRepository.findById(competencyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Competency> response = competencyController.updateCompetency(competencyId, competencyDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteCompetency_CompetencyExists() {
        // Arrange
        int competencyId = 1;
        Competency competency = new Competency();
        when(competencyRepository.findById(competencyId)).thenReturn(Optional.of(competency));

        // Act
        ResponseEntity<Void> response = competencyController.deleteCompetency(competencyId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(competencyRepository, times(1)).delete(competency);
    }

    @Test
    void testDeleteCompetency_CompetencyNotExists() {
        // Arrange
        int competencyId = 1;
        when(competencyRepository.findById(competencyId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = competencyController.deleteCompetency(competencyId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(competencyRepository, never()).delete(any());
    }
}
