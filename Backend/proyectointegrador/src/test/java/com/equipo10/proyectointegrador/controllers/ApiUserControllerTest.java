package com.equipo10.proyectointegrador.controllers;

import com.equipo10.proyectointegrador.dto.ApiUserDTO;
import com.equipo10.proyectointegrador.security.ApiUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link ApiUserController} class.
 * Author: NatalyR
 */
class ApiUserControllerTest {

    @Mock
    private ApiUserServiceImpl apiUserService;

    @InjectMocks
    private ApiUserController apiUserController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for inserting a new user.
     */
    /* @Test
    void testInsertUser() {
        // Arrange
        ApiUserDTO userDTO = new ApiUserDTO();

        System.out.println("Executing testInsertUser...");

        // Act
        ResponseEntity<String> response = apiUserController.insertUser(userDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario creado correctamente", response.getBody());
        verify(apiUserService, times(1)).insertUser(userDTO);

        System.out.println("testInsertUser executed successfully.");
    }*/

    /**
     * Test case for getting a user by their username.
     */
    /*@Test
    void testGetUserByUserName() {
        // Arrange
        String userName = "john.doe";

        System.out.println("Executing testGetUserByUserName...");

        // Act
        Optional<ApiUserDTO> result = apiUserController.getUserByUserName(userName);

        // Perform assertions

        System.out.println("testGetUserByUserName executed successfully.");
    }

    /**
     * Test case for getting all users.
     */
    @Test
    void testGetAllExperienceHandler() {
        System.out.println("Executing testGetAllExperienceHandler...");

        // Act
        List<ApiUserDTO> result = apiUserController.getAllExperienceHandler();

        // Perform assertions

        System.out.println("testGetAllExperienceHandler executed successfully.");
    }

    /**
     * Test case for deleting a user.
     */
    @Test
    void testDeleteUser() {
        // Arrange
        int userId = 1;

        System.out.println("Executing testDeleteUser...");

        // Act
        ResponseEntity<String> response = apiUserController.deleteUser(userId);

        // Perform assertions

        System.out.println("testDeleteUser executed successfully.");
    }

    /**
     * Test case for updating a user.
     */
    @Test
    void testUpdateUser() {
        // Arrange
        int userId = 1;
        ApiUserDTO apiUserDTO = new ApiUserDTO();

        System.out.println("Executing testUpdateUser...");

        // Act
        ResponseEntity<String> response = apiUserController.updateRole(userId, apiUserDTO);

        // Perform assertions

        System.out.println("testUpdateUser executed successfully.");
    }
}


