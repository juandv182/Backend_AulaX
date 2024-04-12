package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.ResourceFileDTO;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.ResourceFile;
import com.example.resourcesactivities.repository.FileRepository;
import com.example.resourcesactivities.repository.MyResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileControllerTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private MyResourceRepository myResourceRepository;

    @InjectMocks
    private FileController fileController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    void testCheckIfFileExists_FileExists() {
        // Arrange
        String fileName = "example.txt";
        ResourceFile file = new ResourceFile();
        when(fileRepository.findByName(fileName)).thenReturn(Optional.of(file));

        // Act
        ResponseEntity<String> response = fileController.checkIfFileExists(fileName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("exists"));
    }

    @Test
    void testCheckIfFileExists_FileDoesNotExist() {
        // Arrange
        String fileName = "example.txt";
        when(fileRepository.findByName(fileName)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = fileController.checkIfFileExists(fileName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("does not exist"));
    }

    @Test
    void testGetFileById_FileExists() throws IOException {
        // Arrange
        UUID fileId = UUID.randomUUID();
        ResourceFile file = new ResourceFile();
        file.setId(fileId);
        file.setName("example.txt");
        file.setFolder("/path/to/file");
        Path filePath = Path.of(file.getFolder());
        when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));

        // Act
        ResponseEntity<Resource> response = fileController.getFileById(fileId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    void testGetFileById_FileDoesNotExist() {
        // Arrange
        UUID fileId = UUID.randomUUID();
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Resource> response = fileController.getFileById(fileId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetFileByMyResourceId_FilesExist() throws Exception {
        // Arrange
        Integer resourceId = 1;
        List<ResourceFile> files = new ArrayList<>();
        ResourceFile file = new ResourceFile();
        files.add(file);
        when(fileRepository.findByMyResourceId(resourceId)).thenReturn(files);

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/files/resource/" + resourceId));

        // Assert
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$").isArray());
        resultActions.andExpect(jsonPath("$[0].name").value(file.getName()));
        // Asegúrate de adaptar las aserciones según la estructura de tu DTO y tu controlador
    }

    @Test
    void testGetFileByMyResourceId_FilesDoNotExist() {
        // Arrange
        Integer resourceId = 1;
        when(fileRepository.findByMyResourceId(resourceId)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ResourceFileDTO>> response = fileController.getFileByMyResourceId(resourceId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void testCreateFiles_WithNoFiles() {
        // Arrange
        List<MultipartFile> files = Collections.emptyList();
        int resourceId = 1;

        // Act
        ResponseEntity<?> response = fileController.createFiles(files, resourceId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Please select at least one file."));
    }

    @Test
    void testCreateFiles_ResourceNotFound() {
        // Arrange
        List<MultipartFile> files = Collections.singletonList(new MockMultipartFile("file", "test.txt", "text/plain", "test file content".getBytes()));
        int resourceId = 1;
        when(myResourceRepository.findMyResourceById(resourceId)).thenReturn(null);

        // Act
        ResponseEntity<?> response = fileController.createFiles(files, resourceId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Resource with id " + resourceId + " not found."));
    }

    @Test
    void testCreateFiles_Success() throws IOException {
        // Arrange
        Integer resourceId = 1;
        MyResource myResource = new MyResource(); // Simulamos un recurso existente
        when(myResourceRepository.findMyResourceById(resourceId)).thenReturn(myResource);

        List<MultipartFile> files = Collections.singletonList(
                createMockMultipartFile("test.txt", "Hello, World!".getBytes()));

        // Act
        ResponseEntity<?> response = fileController.createFiles(files, resourceId);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, ((List<ResourceFile>) response.getBody()).size()); // Verifica que se haya guardado un archivo

        // Verifica que se haya guardado el archivo correctamente
        verify(fileRepository, times(1)).save(any(ResourceFile.class));
    }

    // Método para crear un MultipartFile simulado
    private MultipartFile createMockMultipartFile(String fileName, byte[] content) {
        return new MockMultipartFile(fileName, fileName, "text/plain", content);
    }

    // You can continue adding more tests for different scenarios...
}
