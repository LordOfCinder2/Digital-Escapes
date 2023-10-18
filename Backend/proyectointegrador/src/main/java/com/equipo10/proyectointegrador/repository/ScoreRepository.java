package com.equipo10.proyectointegrador.repository;

import com.equipo10.proyectointegrador.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Integer> {


    @Query("SELECT s FROM Score s WHERE experience.id = ?1")
    List<Score> getAllScoreByExp(Integer id);

    @Query(value = "SELECT ROUND(AVG(rating),1) FROM Score s WHERE experience.id = ?1")
    Optional<Double> getAvgByProductId(Integer id);

}
