package com.equipo10.proyectointegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "characteristics")
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "characteristicSet")
    @JsonIgnore
    @BatchSize(size = 10)
    private Set<Experience> experiences = new HashSet<>();

    @PreRemove
    public void removeExperienceAssociations() {
        for (Experience e : this.experiences) {
            e.getCharacteristicSet().remove(this);
        }
    }

}


