package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.Course;
import com.example.resourcesactivities.repository.CourseRepository;
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

class CourseControllerTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCourses() {
        // Arrange
        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<Course> result = courseController.getAllCourses();

        // Assert
        assertEquals(courses.size(), result.size());
    }

    @Test
    void testGetCourseById_CourseExists() {
        // Arrange
        int courseId = 1;
        Course course = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        ResponseEntity<Course> response = courseController.getCourseById(courseId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
    }

    @Test
    void testGetCourseById_CourseNotExists() {
        // Arrange
        int courseId = 1;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Course> response = courseController.getCourseById(courseId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateCourse() {
        // Arrange
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);

        // Act
        ResponseEntity<Course> response = courseController.createCourse(course);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(course, response.getBody());
    }

    @Test
    void testUpdateCourse_CourseExists() {
        // Arrange
        int courseId = 1;
        Course existingCourse = new Course();
        Course courseDetails = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);

        // Act
        ResponseEntity<Course> response = courseController.updateCourse(courseId, courseDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingCourse, response.getBody());
    }

    @Test
    void testUpdateCourse_CourseNotExists() {
        // Arrange
        int courseId = 1;
        Course courseDetails = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Course> response = courseController.updateCourse(courseId, courseDetails);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteCourse_CourseExists() {
        // Arrange
        int courseId = 1;
        Course course = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    void testDeleteCourse_CourseNotExists() {
        // Arrange
        int courseId = 1;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(courseRepository, never()).delete(any());
    }
}
