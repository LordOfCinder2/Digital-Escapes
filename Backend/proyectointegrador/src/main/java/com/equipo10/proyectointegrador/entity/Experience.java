package com.equipo10.proyectointegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Experiences", indexes = {@Index(columnList = "category_id", name = "categories_index")})
public class Experience {

    private String name;
    private String description;
    private Integer price;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "experienceSlug")
    private String experienceSlug;
    private String location;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @BatchSize(size = 10)
    private Category category;


    @ManyToMany
    @JoinTable(name = "characteristic_list", joinColumns = @JoinColumn(name = "experience_id"), inverseJoinColumns = @JoinColumn(name = "characteristic_id"))
    @BatchSize(size = 10)
    private Set<Characteristic> characteristicSet = new HashSet<>();


    // UTILS

    public void addCharacteristic(Characteristic c) {
        this.characteristicSet.add(c);
        c.getExperiences().add(this);
    }

    public void removeCharacteristic(Characteristic c) {
        this.characteristicSet.remove(c);
        c.getExperiences().remove(this);
    }

    @PreRemove
    public void removeCharAsso() {
        for (Characteristic c : this.characteristicSet) {
            c.getExperiences().remove(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Experience that = (Experience) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
