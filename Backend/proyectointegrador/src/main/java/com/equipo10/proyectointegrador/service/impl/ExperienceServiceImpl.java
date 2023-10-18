package com.equipo10.proyectointegrador.service.impl;

import com.equipo10.proyectointegrador.dto.ExperienceDTO;
import com.equipo10.proyectointegrador.entity.Experience;
import com.equipo10.proyectointegrador.entity.Pagination;
import com.equipo10.proyectointegrador.exceptions.ResourceAlreadyExistExeption;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.repository.CharacteristicRepository;
import com.equipo10.proyectointegrador.repository.ExperienceRepository;
import com.equipo10.proyectointegrador.service.ExperienceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    private final static Logger log = LogManager.getFormatterLogger();

    @Override
    public Optional<ExperienceDTO> searchExperience(Integer id) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        ExperienceDTO experienceDTO;
        Experience experience = null;
        Optional<Experience> optionalExperience = experienceRepository.findById(id);
        if (optionalExperience.isPresent()) {
            experience = optionalExperience.get();
        } else {
            log.error("The experience does not exist in the DB");
            throw new ResourceNotFoundException("La experiencia no existe en la base de datos");
        }
        experienceDTO = objectMapper.convertValue(experience, ExperienceDTO.class);
        log.info("Experience found!");
        return Optional.of(experienceDTO);
    }

    @Override
    public Optional<ExperienceDTO> findExperienceByName(String name) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        ExperienceDTO experienceDTO;
        Experience experience = null;
        Optional<Experience> optionalExperience = experienceRepository.findByName(name);
        if (optionalExperience.isPresent()) {
            experience = optionalExperience.get();
        } else {
            log.error("The experience does not exist in the DB");
            throw new ResourceNotFoundException("La experiencia no existe en la base de datos");
        }
        experienceDTO = objectMapper.convertValue(experience, ExperienceDTO.class);
        return Optional.of(experienceDTO);
    }

    @Override
    public Optional<ExperienceDTO> findExperienceById(Integer id) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        ExperienceDTO experienceDTO;
        Experience experience = null;
        Optional<Experience> optionalExperience = experienceRepository.findById(id);
        if (optionalExperience.isPresent()) {
            experience = optionalExperience.get();
        } else {
            log.error("The experience does not exist in the DB");
            throw new ResourceNotFoundException("La experiencia no existe en la base de datos");
        }
        experienceDTO = objectMapper.convertValue(experience, ExperienceDTO.class);
        return Optional.of(experienceDTO);
    }

    @Override
    public Optional<ExperienceDTO> findExperienceBySlug(String slug) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        ExperienceDTO experienceDTO;
        Experience experience = null;
        Optional<Experience> optionalExperience = experienceRepository.findByExperienceSlug(slug);
        if (optionalExperience.isPresent()) {
            experience = optionalExperience.get();
        } else {
            log.error("The experience does not exist in the DB");
            throw new ResourceNotFoundException("La experiencia no existe en la base de datos");
        }
        experienceDTO = objectMapper.convertValue(experience, ExperienceDTO.class);
        return Optional.of(experienceDTO);
    }

    @Override
    public List<ExperienceDTO> searchAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ExperienceDTO> experienceDTOList = new ArrayList<>();
        List<Experience> experienceList = experienceRepository.findAll();
        for (Experience experience : experienceList
        ) {
            experienceDTOList.add(objectMapper.convertValue(experience, ExperienceDTO.class));
        }
        return experienceDTOList;
    }

    @Override
    public Pagination getAllPaginated(Integer page, Integer elements, String sortBy) {
        ObjectMapper objectMapper = new ObjectMapper();
        PageRequest paging = PageRequest.of(page, elements, Sort.by(sortBy));
        Page<Experience> pagedResult = experienceRepository.findAll(paging);
        List<ExperienceDTO> pagedExperienceDTO = new ArrayList<>();
        Long totalElements = pagedResult.getTotalElements();
        int totalPages = pagedResult.getTotalPages();
        Pagination pagination = new Pagination(pagedExperienceDTO, totalElements, totalPages);

        if (pagedResult.hasContent()) {
            for (Experience experience : pagedResult
            ) {
                pagedExperienceDTO.add(objectMapper.convertValue(experience, ExperienceDTO.class));
            }
            Collections.shuffle(pagedExperienceDTO);
        }
        return pagination;
    }

    @Override
    public Pagination findExperiencesByCategory(Integer categoryId, Integer page, Integer elements) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        PageRequest paging = PageRequest.of(page, elements);
        Page<Experience> pagedResult = experienceRepository.findExperiencesByCategory(categoryId, paging);
        List<ExperienceDTO> experienceDTOList = new ArrayList<>();
        Long totalElements = pagedResult.getTotalElements();
        int totalPages = pagedResult.getTotalPages();
        Pagination pagination = new Pagination(experienceDTOList, totalElements, totalPages);
        for (Experience experience : pagedResult) {
            experienceDTOList.add(objectMapper.convertValue(experience, ExperienceDTO.class));
        }
        return pagination;
    }

    @Override
    public void insertExperience(ExperienceDTO experienceDTO) throws ResourceAlreadyExistExeption {
        ObjectMapper objectMapper = new ObjectMapper();
        Experience experience = objectMapper.convertValue(experienceDTO, Experience.class);
        if (nameAlreadyInUse(experience.getName())) {
            throw new ResourceAlreadyExistExeption("Ya existe una experiencia con ese nombre");
        }
        experienceRepository.save(experience);
        log.info("Experience added!");
    }

    @Transactional
    @Override
    public void deleteExperience(Integer id) {

        experienceRepository.deleteById(id);

        log.info("Experience deleted");
    }

    @Override
    public void updateExperience(ExperienceDTO experienceDTO, Integer id) {
        ObjectMapper objectMapper = new ObjectMapper();
        Experience experience1 = experienceRepository.getReferenceById(id);
        Experience experience = objectMapper.convertValue(experienceDTO, Experience.class);
        if (experience.getName() != null)
            experience1.setName(experience.getName());
        if (experience.getDescription() != null)
            experience1.setDescription(experience.getDescription());
        if (experience.getPrice() != null)
            experience1.setPrice(experience.getPrice());
        if (experience.getImgUrl() != null)
            experience1.setImgUrl(experience.getImgUrl());
        if (experience.getExperienceSlug() != null)
            experience1.setExperienceSlug(experience.getExperienceSlug());
        if (experience.getCategory() != null)
            experience1.setCategory(experience.getCategory());
        if (experience.getLocation() != null)
            experience1.setLocation(experience.getLocation());
        if (experience.getCharacteristicSet() != null)
            experience1.setCharacteristicSet(experience.getCharacteristicSet());
        experienceRepository.save(experience1);
    }


    // UTILS

    private boolean nameAlreadyInUse(String name) {
        return experienceRepository.findByName(name).isPresent();
    }

}
