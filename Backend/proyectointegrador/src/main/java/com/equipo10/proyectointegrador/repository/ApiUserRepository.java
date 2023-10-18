package com.equipo10.proyectointegrador.repository;

import com.equipo10.proyectointegrador.entity.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ApiUserRepository extends JpaRepository<ApiUser, Integer> {

    @Query("SELECT u FROM ApiUser u WHERE u.email = ?1")
    Optional<ApiUser> findByEmail(String email);
}
