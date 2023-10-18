package com.equipo10.proyectointegrador.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExperienceTest {

    @Test
    void testGetterAndSetterMethods() {
        // Create an instance of Experience and test its getter and setter methods
        Experience experience = new Experience();
        experience.setId(1);
        experience.setName("Experience Name");

        assertEquals(1, experience.getId());
        assertEquals("Experience Name", experience.getName());
    }

}