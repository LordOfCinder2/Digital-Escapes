package com.equipo10.proyectointegrador.controllers;

import com.equipo10.proyectointegrador.dto.ExperienceDTO;
import com.equipo10.proyectointegrador.entity.Pagination;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.service.impl.ExperienceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ExperienceController} class.
 * Author: NatalyR
 */

class ExperienceControllerTest {

    @Mock
    private ExperienceServiceImpl experienceService;

    @InjectMocks
    private ExperienceController experienceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExperienceHandler() throws ResourceNotFoundException {
        int experienceId = 1;
        ExperienceDTO experienceDTO = new ExperienceDTO();

        System.out.println("Test 1: Executing testGetExperienceHandler...");

        when(experienceService.searchExperience(experienceId)).thenReturn(Optional.of(experienceDTO));

        Optional<ExperienceDTO> result = experienceController.getExperienceHandler(experienceId);

        assertEquals(Optional.of(experienceDTO), result);
        verify(experienceService, times(1)).searchExperience(experienceId);

        System.out.println("testGetExperienceHandler executed successfully.");
    }

    @Test
    void testGetAllExperienceHandler() {
        System.out.println("Test 2: Executing testGetAllExperienceHandler...");

        List<ExperienceDTO> experienceList = List.of(new ExperienceDTO(), new ExperienceDTO());

        when(experienceService.searchAll()).thenReturn(experienceList);

        List<ExperienceDTO> result = experienceController.getAllExperienceHandler();

        assertEquals(experienceList, result);
        verify(experienceService, times(1)).searchAll();

        System.out.println("testGetAllExperienceHandler executed successfully.");
    }

    /* @Test
    void testPostExperienceHandler() throws ResourceNotFoundException {
        ExperienceDTO experienceDTO = new ExperienceDTO();

        System.out.println("Test 3: Executing testPostExperienceHandler...");

        ResponseEntity<String> response = experienceController.postExperienceHandler(experienceDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Experiencia agregada", response.getBody());
        verify(experienceService, times(1)).insertExperience(experienceDTO);

        System.out.println("testPostExperienceHandler executed successfully.");
    }*/

    @Test
    void testDeleteExperienceHandler() {
        int experienceId = 1;

        System.out.println("Test 4: Executing testDeleteExperienceHandler...");

        ResponseEntity<String> response = experienceController.deleteExperienceHandler(experienceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Experiencia borrada", response.getBody());
        verify(experienceService, times(1)).deleteExperience(experienceId);

        System.out.println("testDeleteExperienceHandler executed successfully.");
    }

    @Test
    void testUpdateExperienceHandler() {
        int experienceId = 1;
        ExperienceDTO experienceDTO = new ExperienceDTO();

        System.out.println("Test 5: Executing testUpdateExperienceHandler...");

        ResponseEntity<String> response = experienceController.updateExperienceHandler(experienceId, experienceDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Experiencia actualizada", response.getBody());
        verify(experienceService, times(1)).updateExperience(experienceDTO, experienceId);

        System.out.println("testUpdateExperienceHandler executed successfully.");
    }

    @Test
    void testGetExperienceByName() throws ResourceNotFoundException {
        String experienceName = "Sample Experience";

        System.out.println("Test 6: Executing testGetExperienceByName...");

        ExperienceDTO experienceDTO = new ExperienceDTO();
        when(experienceService.findExperienceByName(experienceName)).thenReturn(Optional.of(experienceDTO));

        Optional<ExperienceDTO> result = experienceController.getExperienceByName(experienceName);

        assertEquals(Optional.of(experienceDTO), result);
        verify(experienceService, times(1)).findExperienceByName(experienceName);

        System.out.println("testGetExperienceByName executed successfully.");
    }

    @Test
    void testGetAllPaginated() {
        int page = 0;
        int elements = 10;
        String sortBy = "id";

        System.out.println("Test 7: Executing testGetAllPaginated...");

        Pagination pagination = new Pagination();
        when(experienceService.getAllPaginated(page, elements, sortBy)).thenReturn(pagination);

        ResponseEntity<Pagination> response = experienceController.getAllPaginated(page, elements, sortBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagination, response.getBody());
        verify(experienceService, times(1)).getAllPaginated(page, elements, sortBy);

        System.out.println("testGetAllPaginated executed successfully.");
    }

    @Test
    void testGetExperienceBySlug() throws ResourceNotFoundException {
        String slug = "sample-experience";

        System.out.println("Test 8: Executing testGetExperienceBySlug...");

        ExperienceDTO experienceDTO = new ExperienceDTO();
        when(experienceService.findExperienceBySlug(slug)).thenReturn(Optional.of(experienceDTO));

        Optional<ExperienceDTO> result = experienceController.getExperienceBySlug(slug);

        assertEquals(Optional.of(experienceDTO), result);
        verify(experienceService, times(1)).findExperienceBySlug(slug);

        System.out.println("testGetExperienceBySlug executed successfully.");
    }
}
