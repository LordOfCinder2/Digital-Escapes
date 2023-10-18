package com.equipo10.proyectointegrador.service;


import com.equipo10.proyectointegrador.dto.ExperienceDTO;
import com.equipo10.proyectointegrador.entity.Pagination;
import com.equipo10.proyectointegrador.exceptions.ResourceAlreadyExistExeption;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {

    Optional<ExperienceDTO> searchExperience(Integer id) throws ResourceNotFoundException;

    List<ExperienceDTO> searchAll();

    void insertExperience(ExperienceDTO experienceDTO) throws ResourceAlreadyExistExeption;

    void deleteExperience(Integer id);

    void updateExperience(ExperienceDTO experienceDTO, Integer id);

    Optional<ExperienceDTO> findExperienceByName(String name) throws ResourceNotFoundException;

    Optional<ExperienceDTO> findExperienceById(Integer id) throws ResourceNotFoundException;

    Pagination getAllPaginated(Integer page, Integer elements, String sortBy);

    Optional<ExperienceDTO> findExperienceBySlug(String slug) throws ResourceNotFoundException;

    Pagination findExperiencesByCategory(Integer categoryId, Integer page, Integer elements) throws ResourceNotFoundException;
}
