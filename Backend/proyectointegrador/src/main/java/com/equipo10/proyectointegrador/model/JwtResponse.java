package com.equipo10.proyectointegrador.model;

import lombok.AllArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
public class JwtResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -8091879091924046844L;

    private final String JWTTOKEN;

    public String getToken() {
        return this.JWTTOKEN;
    }
}
