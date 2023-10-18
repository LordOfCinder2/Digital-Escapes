package com.equipo10.proyectointegrador.service;

import com.equipo10.proyectointegrador.dto.ReservationDTOBuilder;
import com.equipo10.proyectointegrador.model.DatePairs;

import java.util.List;

public interface ReservationService {

    Integer postReservation(ReservationDTOBuilder reservationDTOBuilder, String token) throws Exception;

    List<DatePairs> datesByExperience(Integer experienceId);
}
