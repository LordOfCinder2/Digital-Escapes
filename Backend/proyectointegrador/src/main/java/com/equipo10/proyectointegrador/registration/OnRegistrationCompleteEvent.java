package com.equipo10.proyectointegrador.registration;

import com.equipo10.proyectointegrador.entity.ApiUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private ApiUser apiUser;

    public OnRegistrationCompleteEvent(ApiUser apiUser, String appUrl) {
        super(apiUser);
        this.apiUser = apiUser;
        this.appUrl = appUrl;
    }
}
