package com.equipo10.proyectointegrador.service.impl;


import com.equipo10.proyectointegrador.dto.ScoreDTOBuilder;
import com.equipo10.proyectointegrador.entity.ApiUser;
import com.equipo10.proyectointegrador.entity.Experience;
import com.equipo10.proyectointegrador.entity.Score;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.repository.ApiUserRepository;
import com.equipo10.proyectointegrador.repository.ExperienceRepository;
import com.equipo10.proyectointegrador.repository.ScoreRepository;
import com.equipo10.proyectointegrador.security.config.JwtTokenUtil;
import com.equipo10.proyectointegrador.service.ScoreService;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ApiUserRepository apiUserRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.secret}")
    private String secret;

    private final static Logger log = LogManager.getFormatterLogger();


    @Override
    public Optional<Double> getAvgByProductId(Integer id) throws ResourceNotFoundException {
        Double average = scoreRepository.getAvgByProductId(id).orElse(null);
        if (average == null) {
            throw new ResourceNotFoundException("no se encontraron calificaciones para la experiencia solicitada");
        }
        return Optional.of(average);
    }

    public List<Integer> getAllScoresByExp(Integer expId) throws ResourceNotFoundException {
        List<Score> scores = scoreRepository.getAllScoreByExp(expId);
        List<Integer> scoreDTOList = new ArrayList<>();
        if (scores.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron calificaciones para la experiencia seleccionada");
        }
        for (Score score : scores
        ) {
            scoreDTOList.add(score.getRating());
        }
        return scoreDTOList;
    }

    @Override
    public void insertScore(ScoreDTOBuilder scoreDTOBuilder, String token) throws Exception {
        Score score = new Score();
        Integer apiUserId = decodeJWTToken(token);
        ApiUser apiUser = apiUserRepository.findById(apiUserId).orElse(null);
        Experience experience = experienceRepository.findById(scoreDTOBuilder.getExperienceId()).orElse(null);
        if (experience == null) {
            throw new ResourceNotFoundException("La experiencia no existe en la base de datos");
        }

        score.setRating(scoreDTOBuilder.getRating());
        score.setExperience(experience);
        score.setApiUser(apiUser);
        scoreRepository.save(score);
        log.info("Score added!");
    }

    // UTILS

    private Integer decodeJWTToken(String fullToken) throws Exception {
        String token = fullToken.substring(7);
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        System.out.println("userId: " + claims.get("userId"));
        Object userId = claims.get("userId");


        return (Integer) claims.get("userId");
    }
}
