package com.equipo10.proyectointegrador.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class CategoryDTO {
    private Integer id;
    private String name;
    private String description;
    private String imgUrl;
}
