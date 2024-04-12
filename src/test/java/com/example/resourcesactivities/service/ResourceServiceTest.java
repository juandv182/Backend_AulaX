package com.example.resourcesactivities.service;

import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.Competency;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.model.Course;
import com.example.resourcesactivities.repository.CompetencyRepository;
import com.example.resourcesactivities.repository.MyResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ResourceServiceTest {

    @Mock
    private MyResourceRepository myResourceRepository;

    @Mock
    private CompetencyRepository competencyRepository;

    @InjectMocks
    private ResourceService resourceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchResources_ByTopicId() {
        // Arrange
        int topicId = 1;
        List<MyResource> expectedResources = Arrays.asList(new MyResource(), new MyResource());
        when(myResourceRepository.findByTopicId(topicId)).thenReturn(expectedResources);

        // Act
        List<MyResource> actualResources = resourceService.searchResources(topicId, null, null);

        // Assert
        assertEquals(expectedResources, actualResources);
    }

    @Test
    public void testSearchResources_WithCompetencies() {
        // Arrange
        List<Integer> competencies = Arrays.asList(1, 2, 3);
        List<MyResource> resources = Arrays.asList(new MyResource(), new MyResource());
        when(myResourceRepository.findAll()).thenReturn(resources);

        // Mock competencyRepository
        List<Integer> topicIds = Arrays.asList(4, 5, 6);
        when(competencyRepository.findById(anyInt())).thenAnswer(invocation -> {
            int competencyId = invocation.getArgument(0);
            Topic topic = new Topic();
            topic.setId(topicIds.get(competencyId - 1));
            Competency competency = new Competency();
            competency.setTopic(topic);
            return Optional.of(competency);
        });

        // Act
        List<MyResource> actualResources = resourceService.searchResources(1, competencies, null);

        // Assert
        assertEquals(0, actualResources.size());
    }
    @Test
    public void testSearchResources_WithCourses() {
        // Arrange
        List<Integer> courses = Arrays.asList(1, 2, 3);
        List<MyResource> resources = Arrays.asList(new MyResource(), new MyResource());
        when(myResourceRepository.findAll()).thenReturn(resources);
        Topic topic = new Topic();
        resources.get(0).setTopic(topic);

        // Mock course data
        List<Integer> courseIds = Arrays.asList(1, 2, 3);
        resources.get(0).getTopic().setCourse(new Course(courseIds.get(0), "Course 1", null, null, null, null));


        // Act
        List<MyResource> actualResources = resourceService.searchResources(1, null, courses);

        // Assert
        assertEquals(0, actualResources.size()); // Assuming both resources match the provided courses
    }



    @Test
    public void testSearchResources_NoFilters() {
        // Arrange
        List<MyResource> resources = Arrays.asList(new MyResource(), new MyResource());
        when(myResourceRepository.findAll()).thenReturn(resources);

        // Act
        List<MyResource> actualResources = resourceService.searchResources(0, null, null);

        // Assert
        assertEquals(resources.size(), actualResources.size());
    }
}

