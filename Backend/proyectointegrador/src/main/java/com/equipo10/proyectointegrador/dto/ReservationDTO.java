package com.equipo10.proyectointegrador.dto;

import com.equipo10.proyectointegrador.entity.ApiUser;
import com.equipo10.proyectointegrador.entity.Experience;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    private Integer id;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private Experience experience;
    private ApiUser apiUser;
}
