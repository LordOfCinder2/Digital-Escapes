package com.equipo10.proyectointegrador.service.impl;

import com.equipo10.proyectointegrador.dto.CharacteristicDTO;
import com.equipo10.proyectointegrador.entity.Characteristic;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.repository.CharacteristicRepository;
import com.equipo10.proyectointegrador.service.CharacteristicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharacteristicServiceImpl implements CharacteristicService {

    @Autowired
    private CharacteristicRepository characteristicRepository;

    private final static Logger log = LogManager.getFormatterLogger();

    @Override
    public Optional<CharacteristicDTO> searchCharacteristic(Integer id) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        CharacteristicDTO characteristicDTO;
        Characteristic characteristic = null;
        Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(id);
        if (optionalCharacteristic.isPresent()) {
            characteristic = optionalCharacteristic.get();
        } else {
            log.error("The characteristic does not exist in the DB");
            throw new ResourceNotFoundException("The characteristic does not exist in the DB");

        }
        characteristicDTO = objectMapper.convertValue(characteristic, CharacteristicDTO.class);
        log.info("Characteristic found!");
        return Optional.of(characteristicDTO);
    }

    @Override
    public List<CharacteristicDTO> searchAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CharacteristicDTO> characteristicDTOList = new ArrayList<>();
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        for (Characteristic characteristic : characteristicList
        ) {
            characteristicDTOList.add(objectMapper.convertValue(characteristic, CharacteristicDTO.class));
        }
        return characteristicDTOList;
    }

    @Override
    public void insertCharacteristic(CharacteristicDTO characteristicDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        Characteristic characteristic = objectMapper.convertValue(characteristicDTO, Characteristic.class);
        characteristicRepository.save(characteristic);
        log.info("Characteristic added!");
    }

    @Override
    public void deleteCharacteristic(Integer id) {
        characteristicRepository.deleteById(id);
        log.info("characteristic deleted");
    }

    @Override
    public void updateCharacteristic(CharacteristicDTO characteristicDTO, Integer id) {
        ObjectMapper objectMapper = new ObjectMapper();
        Characteristic characteristic1 = characteristicRepository.getReferenceById(id);
        Characteristic characteristic = objectMapper.convertValue(characteristicDTO, Characteristic.class);
        if (characteristic.getName() != null)
            characteristic1.setName(characteristic.getName());
        if (characteristic.getId() != null)
            characteristic1.setId(characteristic.getId());
        characteristicRepository.save(characteristic);
    }

    @Override
    public Optional<CharacteristicDTO> findCharacteristicByName(String name) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        CharacteristicDTO characteristicDTO;
        Characteristic characteristic = null;
        Optional<Characteristic> optionalCharacteristic = characteristicRepository.findByName(name);
        if (optionalCharacteristic.isPresent()) {
            characteristic = optionalCharacteristic.get();
        } else {
            log.error("The characteristic does not exist in the DB");
            throw new ResourceNotFoundException("The characteristic does not exist in the DB");
        }
        characteristicDTO = objectMapper.convertValue(characteristic, CharacteristicDTO.class);
        return Optional.ofNullable(characteristicDTO);
    }

    @Override
    public Optional<CharacteristicDTO> findCharacteristicById(Integer id) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        CharacteristicDTO characteristicDTO;
        Characteristic characteristic = null;
        Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(id);
        if (optionalCharacteristic.isPresent()) {
            characteristic = optionalCharacteristic.get();
        } else {
            log.error("The characteristic does not exist in the DB");
            throw new ResourceNotFoundException("The characteristic does not exist in the DB");
        }
        characteristicDTO = objectMapper.convertValue(characteristic, CharacteristicDTO.class);
        return Optional.of(characteristicDTO);
    }

}


