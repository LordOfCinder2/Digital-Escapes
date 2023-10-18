package com.equipo10.proyectointegrador.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "departure_date", columnDefinition = "DATE")
    private LocalDate departureDate;
    @Column(name = "return_date", columnDefinition = "DATE")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "experience_id", nullable = false)
    @JsonIgnore
    @BatchSize(size = 10)
    private Experience experience;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @BatchSize(size = 10)
    private ApiUser apiUser;
}
