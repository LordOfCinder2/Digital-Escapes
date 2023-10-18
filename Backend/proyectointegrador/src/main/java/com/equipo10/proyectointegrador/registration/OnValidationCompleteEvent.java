package com.equipo10.proyectointegrador.registration;

import com.equipo10.proyectointegrador.entity.ApiUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class OnValidationCompleteEvent extends ApplicationEvent {

    private ApiUser apiUser;

    public OnValidationCompleteEvent(ApiUser apiUser) {
        super(apiUser);
        this.apiUser = apiUser;
    }
}
