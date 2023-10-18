package com.equipo10.proyectointegrador.service;

import com.equipo10.proyectointegrador.dto.ScoreDTOBuilder;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;

import java.util.Optional;

public interface ScoreService {
    Optional<Double> getAvgByProductId(Integer id) throws ResourceNotFoundException;

    void insertScore(ScoreDTOBuilder scoreDTOBuilder, String token) throws Exception;

}
