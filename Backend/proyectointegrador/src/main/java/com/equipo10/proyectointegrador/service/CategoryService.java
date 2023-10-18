package com.equipo10.proyectointegrador.service;

import com.equipo10.proyectointegrador.dto.CategoryDTO;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<CategoryDTO> searchCategory(Integer id) throws ResourceNotFoundException;

    List<CategoryDTO> searchAll();

    void insertCategory(CategoryDTO categoryDTO);

    void deleteCategory(Integer id);

    void updateCategory(CategoryDTO categoryDTO, Integer id);

    Optional<CategoryDTO> findCategoryByName(String name) throws ResourceNotFoundException;

    Optional<CategoryDTO> findCategoryById(Integer id) throws ResourceNotFoundException;


}
