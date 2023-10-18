package com.equipo10.proyectointegrador.controllers;

import com.equipo10.proyectointegrador.dto.ExperienceDTO;
import com.equipo10.proyectointegrador.entity.Pagination;
import com.equipo10.proyectointegrador.exceptions.ResourceAlreadyExistExeption;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.service.impl.ExperienceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/experience")
public class ExperienceController {

    @Autowired
    private ExperienceServiceImpl serviceExperienceImpl;


    @GetMapping
    public Optional<ExperienceDTO> getExperienceHandler(@RequestParam Integer id) throws ResourceNotFoundException {
        return serviceExperienceImpl.searchExperience(id);
    }

    @GetMapping("/all")
    public List<ExperienceDTO> getAllExperienceHandler() {
        return serviceExperienceImpl.searchAll();
    }

    @PostMapping
    public ResponseEntity<String> postExperienceHandler(@RequestBody ExperienceDTO experienceDTO) throws ResourceAlreadyExistExeption {
        serviceExperienceImpl.insertExperience(experienceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Experiencia agregada");
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteExperienceHandler(@RequestParam Integer id) {
        serviceExperienceImpl.deleteExperience(id);
        return ResponseEntity.status(HttpStatus.OK).body("Experiencia borrada");
    }

    @PutMapping
    public ResponseEntity<String> updateExperienceHandler(@RequestParam Integer id, @RequestBody ExperienceDTO experienceDTO) {
        serviceExperienceImpl.updateExperience(experienceDTO, id);
        return ResponseEntity.ok("Experiencia actualizada");
    }

    @GetMapping("/experienceName")
    public Optional<ExperienceDTO> getExperienceByName(@RequestParam String expName) throws ResourceNotFoundException {
        return serviceExperienceImpl.findExperienceByName(expName);
    }

    @GetMapping("/allPaginated")
    public ResponseEntity<Pagination> getAllPaginated(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer elements, @RequestParam(defaultValue = "id") String sortBy) {
        Pagination pagination = serviceExperienceImpl.getAllPaginated(page, elements, sortBy);
        return new ResponseEntity<>(pagination, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/experienceSlug")
    public Optional<ExperienceDTO> getExperienceBySlug(@RequestParam String slug) throws ResourceNotFoundException {
        return serviceExperienceImpl.findExperienceBySlug(slug);
    }

    @GetMapping("/experiencesByCategory")
    public Pagination getAllExperiencesByCategory(@RequestParam Integer categoryId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer elements) throws ResourceNotFoundException {
        return serviceExperienceImpl.findExperiencesByCategory(categoryId, page, elements);
    }

}
