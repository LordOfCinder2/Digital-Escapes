package com.equipo10.proyectointegrador.service.impl;


import com.equipo10.proyectointegrador.dto.CategoryDTO;
import com.equipo10.proyectointegrador.entity.Category;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.repository.CategoryRepository;
import com.equipo10.proyectointegrador.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private final static Logger log = LogManager.getFormatterLogger();

    @Override
    public Optional<CategoryDTO> searchCategory(Integer id) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryDTO categoryDTO;
        Category category = null;
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
        } else {
            log.error("The category does not exist in the DB");
            throw new ResourceNotFoundException("The category does not exist in the DB");

        }
        categoryDTO = objectMapper.convertValue(category, CategoryDTO.class);
        log.info("Category found!");
        return Optional.of(categoryDTO);
    }

    @Override
    public List<CategoryDTO> searchAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();
        for (Category category : categoryList
        ) {
            categoryDTOList.add(objectMapper.convertValue(category, CategoryDTO.class));
        }
        return categoryDTOList;
    }


    @Override
    public Optional<CategoryDTO> findCategoryByName(String name) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryDTO categoryDTO;
        Category category = null;
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
        } else {
            log.error("The category does not exist in the DB");
            throw new ResourceNotFoundException("The category does not exist in the DB");
        }
        categoryDTO = objectMapper.convertValue(category, CategoryDTO.class);
        return Optional.ofNullable(categoryDTO);
    }

    @Override
    public Optional<CategoryDTO> findCategoryById(Integer id) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryDTO categoryDTO;
        Category category = null;
        Optional<Category> optionalcategory = categoryRepository.findById(id);
        if (optionalcategory.isPresent()) {
            category = optionalcategory.get();
        } else {
            log.error("The category does not exist in the DB");
            throw new ResourceNotFoundException("The category does not exist in the DB");
        }
        categoryDTO = objectMapper.convertValue(category, CategoryDTO.class);
        return Optional.of(categoryDTO);
    }

    @Override
    public void insertCategory(CategoryDTO categoryDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        Category category = objectMapper.convertValue(categoryDTO, Category.class);
        categoryRepository.save(category);
        log.info("Category added!");
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
        log.info("category deleted");
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO, Integer id) {
        ObjectMapper objectMapper = new ObjectMapper();
        Category category1 = categoryRepository.getReferenceById(id);
        Category category = objectMapper.convertValue(categoryDTO, Category.class);
        if (category.getName() != null)
            category1.setName(category.getName());
        if (category.getDescription() != null)
            category1.setDescription(category.getDescription());
        if (category.getImgUrl() != null)
            category1.setImgUrl(category.getImgUrl());
        categoryRepository.save(category1);
    }

}
