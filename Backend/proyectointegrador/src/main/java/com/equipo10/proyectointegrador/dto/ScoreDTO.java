package com.equipo10.proyectointegrador.dto;

import com.equipo10.proyectointegrador.entity.ApiUser;
import com.equipo10.proyectointegrador.entity.Experience;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ScoreDTO {
    private Integer Id;
    private Integer rating;
    private Experience experience;
    private ApiUser apiUser;
}