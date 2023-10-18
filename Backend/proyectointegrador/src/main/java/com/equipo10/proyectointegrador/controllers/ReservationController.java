package com.equipo10.proyectointegrador.controllers;


import com.equipo10.proyectointegrador.dto.ReservationDTOBuilder;
import com.equipo10.proyectointegrador.model.DatePairs;
import com.equipo10.proyectointegrador.service.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationServiceImpl reservationService;

    @GetMapping("/datesByExp")
    public ResponseEntity<List<DatePairs>> findDatesByExp(@RequestParam Integer expId) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.datesByExperience(expId));
    }

    @PostMapping
    public ResponseEntity<Integer> makeReservation(@RequestBody ReservationDTOBuilder reservationDTOBuilder, @RequestHeader(name = "Authorization") String token) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.postReservation(reservationDTOBuilder, token));
    }


}
