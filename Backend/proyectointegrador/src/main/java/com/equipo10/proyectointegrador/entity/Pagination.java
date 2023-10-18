package com.equipo10.proyectointegrador.entity;

import com.equipo10.proyectointegrador.dto.ExperienceDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Pagination {

    private List<ExperienceDTO> experienceDTOList = new ArrayList<>();
    private Long totalElements;
    private int totalPages;
}
