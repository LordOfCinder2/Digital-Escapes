package com.equipo10.proyectointegrador.dto;

import com.equipo10.proyectointegrador.entity.ApiUserRoles;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class ApiUserDTO {
    private Integer id;
    private String name;
    private String username;
    private Integer phone;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ApiUserRoles apiUserRoles;
    private boolean enabled;
}
