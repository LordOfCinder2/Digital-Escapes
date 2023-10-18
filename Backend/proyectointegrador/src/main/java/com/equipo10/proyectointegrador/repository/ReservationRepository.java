package com.equipo10.proyectointegrador.repository;

import com.equipo10.proyectointegrador.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r WHERE experience.id = ?1")
    List<Reservation> reservationByExperience(Integer expId);

}
