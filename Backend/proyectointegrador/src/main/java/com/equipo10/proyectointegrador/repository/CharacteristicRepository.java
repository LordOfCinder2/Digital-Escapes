package com.equipo10.proyectointegrador.repository;

import com.equipo10.proyectointegrador.dto.CharacteristicDTO;
import com.equipo10.proyectointegrador.entity.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Integer> {
    @Query("SELECT c FROM Characteristic c WHERE c.name = ?1")
    Optional<Characteristic> findByName(String name);

    @Query("SELECT c FROM Characteristic c WHERE c.id = ?1")
    Optional<Characteristic> findById(Integer id);

    @Query("SELECT e FROM Characteristic c LEFT JOIN Experience e WHERE c.id = e.id")
    List<CharacteristicDTO> findCharacteristicByExperience(Integer id);

}
