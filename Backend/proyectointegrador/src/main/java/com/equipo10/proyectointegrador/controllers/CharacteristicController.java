package com.equipo10.proyectointegrador.controllers;

import com.equipo10.proyectointegrador.dto.CharacteristicDTO;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.service.impl.CharacteristicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/characteristic")
public class CharacteristicController {
    @Autowired
    private CharacteristicServiceImpl serviceCharacteristicImpl;

    @GetMapping
    public Optional<CharacteristicDTO> getCharacteristicHandler(@RequestParam Integer id) throws ResourceNotFoundException {
        return serviceCharacteristicImpl.searchCharacteristic(id);
    }

    @GetMapping("/all")
    public List<CharacteristicDTO> getAllCharacteristicHandler() {
        return serviceCharacteristicImpl.searchAll();
    }

    @PostMapping
    public ResponseEntity<String> postCharacteristicHandler(@RequestBody CharacteristicDTO characteristicDTO) {
        serviceCharacteristicImpl.insertCharacteristic(characteristicDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Characteristic added");
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteCharacteristicHandler(@PathVariable Integer id) {
        serviceCharacteristicImpl.deleteCharacteristic(id);
        return ResponseEntity.status(HttpStatus.OK).body("Characteristic deleted");
    }

    @PutMapping
    public ResponseEntity<String> updateCharacteristicHandler(@RequestParam Integer id, @RequestBody CharacteristicDTO characteristicDTO) {
        serviceCharacteristicImpl.updateCharacteristic(characteristicDTO, id);
        return ResponseEntity.ok("Characteristic Updated");
    }

    @GetMapping("/characteristicName")
    public Optional<CharacteristicDTO> getCharacteristicByName(@RequestParam String characteristicName) throws ResourceNotFoundException {
        return serviceCharacteristicImpl.findCharacteristicByName(characteristicName);
    }


}
