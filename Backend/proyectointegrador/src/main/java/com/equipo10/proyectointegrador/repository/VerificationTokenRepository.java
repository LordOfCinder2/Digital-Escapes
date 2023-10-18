package com.equipo10.proyectointegrador.repository;

import com.equipo10.proyectointegrador.entity.ApiUser;
import com.equipo10.proyectointegrador.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
    VerificationToken findByToken(String token);

    Optional<VerificationToken> findByApiUser(ApiUser apiUser);
}
