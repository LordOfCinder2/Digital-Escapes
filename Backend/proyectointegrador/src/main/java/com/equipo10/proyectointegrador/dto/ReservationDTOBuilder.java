package com.equipo10.proyectointegrador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDTOBuilder {
    private String departureDate;
    private String returnDate;
    private Integer experienceId;
}
