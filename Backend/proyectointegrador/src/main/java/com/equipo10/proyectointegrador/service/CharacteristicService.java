package com.equipo10.proyectointegrador.service;

import com.equipo10.proyectointegrador.dto.CharacteristicDTO;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CharacteristicService {
    Optional<CharacteristicDTO> searchCharacteristic(Integer id) throws ResourceNotFoundException;

    List<CharacteristicDTO> searchAll();

    void insertCharacteristic(CharacteristicDTO characteristicDTO);

    void deleteCharacteristic(Integer id);

    void updateCharacteristic(CharacteristicDTO characteristicDTO, Integer id);

    Optional<CharacteristicDTO> findCharacteristicByName(String name) throws ResourceNotFoundException;

    Optional<CharacteristicDTO> findCharacteristicById(Integer id) throws ResourceNotFoundException;

}
