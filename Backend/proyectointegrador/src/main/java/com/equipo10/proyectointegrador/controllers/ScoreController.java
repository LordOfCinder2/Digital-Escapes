package com.equipo10.proyectointegrador.controllers;


import com.equipo10.proyectointegrador.dto.ScoreDTOBuilder;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.service.impl.ScoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreServiceImpl scoreServiceImpl;


    @GetMapping("/all")
    public List<Integer> getAllScoresByExp(@RequestParam Integer expId) throws ResourceNotFoundException {
        return scoreServiceImpl.getAllScoresByExp(expId);
    }

    @GetMapping("/average")
    public ResponseEntity<Double> getAvgByProductId(@RequestParam Integer id) throws ResourceNotFoundException {
        Optional<Double> avgByProductId = scoreServiceImpl.getAvgByProductId(id);
        return avgByProductId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> insertScore(@RequestBody ScoreDTOBuilder scoreDTOBuilder, @RequestHeader(name = "Authorization") String token) throws Exception {
        scoreServiceImpl.insertScore(scoreDTOBuilder, token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se registró su calificación");
    }

}
