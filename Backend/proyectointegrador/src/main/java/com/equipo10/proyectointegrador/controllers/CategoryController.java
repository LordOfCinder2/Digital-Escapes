package com.equipo10.proyectointegrador.controllers;


import com.equipo10.proyectointegrador.dto.CategoryDTO;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl serviceCategoryImpl;


    @GetMapping
    public Optional<CategoryDTO> getCategoryHandler(@RequestParam Integer id) throws ResourceNotFoundException {
        return serviceCategoryImpl.searchCategory(id);
    }

    @GetMapping("/all")
    public List<CategoryDTO> getAllCategoryHandler() {
        return serviceCategoryImpl.searchAll();
    }

    @PostMapping
    public ResponseEntity<String> postCategoryHandler(@RequestBody CategoryDTO categoryDTO) {
        serviceCategoryImpl.insertCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category added");
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteCategoryHandler(@PathVariable Integer id) {
        serviceCategoryImpl.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body("Category deleted");
    }

    @PutMapping
    public ResponseEntity<String> updateCategoryHandler(@RequestParam Integer id, @RequestBody CategoryDTO categoryDTO) {
        serviceCategoryImpl.updateCategory(categoryDTO, id);
        return ResponseEntity.ok("Category Updated");
    }

    @GetMapping("/categoryName")
    public Optional<CategoryDTO> getCategoryByName(@RequestParam String expName) throws ResourceNotFoundException {
        return serviceCategoryImpl.findCategoryByName(expName);
    }
}
