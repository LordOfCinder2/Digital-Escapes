package com.equipo10.proyectointegrador.dto;

import com.equipo10.proyectointegrador.entity.Category;
import com.equipo10.proyectointegrador.entity.Characteristic;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "characteristicSet")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class ExperienceDTO {
    private Integer id;
    private String name;
    private String description;
    private String imgUrl;
    private Integer price;
    private String experienceSlug;
    private Category category;
    private String location;
    private Set<Characteristic> characteristicSet;
}