package com.equipo10.proyectointegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "apiuser_id")
    private ApiUser apiUser;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;
}
