package com.equipo10.proyectointegrador.repository;

import com.equipo10.proyectointegrador.entity.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

    @Query("SELECT e FROM Experience e WHERE e.name = ?1")
    Optional<Experience> findByName(String name);

    @Query("SELECT e FROM Experience e WHERE e.id = ?1")
    Optional<Experience> findById(Integer id);

    @Query("SELECT e FROM Experience e WHERE e.experienceSlug = ?1")
    Optional<Experience> findByExperienceSlug(String slug);

    @Query("SELECT e FROM Experience e WHERE e.category.id = ?1")
    Page<Experience> findExperiencesByCategory(Integer categoryId, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM characteristic_list ec WHERE ec.experience_id = ?1", nativeQuery = true)
    void deleteExperienceAsso(Integer id);

}
