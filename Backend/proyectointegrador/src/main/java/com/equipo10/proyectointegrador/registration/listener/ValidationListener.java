package com.equipo10.proyectointegrador.registration.listener;

import com.equipo10.proyectointegrador.entity.ApiUser;
import com.equipo10.proyectointegrador.registration.OnValidationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class ValidationListener implements ApplicationListener<OnValidationCompleteEvent> {

    @Autowired
    JavaMailSender mailSender;


    @Override
    public void onApplicationEvent(OnValidationCompleteEvent event) {
        this.confirmValidation(event);
    }

    private void confirmValidation(OnValidationCompleteEvent event) {
        ApiUser apiUser = event.getApiUser();
        SimpleMailMessage email = new SimpleMailMessage();
        String recipientAddress = apiUser.getEmail();
        String subject = "Correcta activación de cuenta";
        String message = "Su cuenta se activó correctamente, ya puede iniciar sesion ingresando al siguiente enlace: http://127.0.0.1:5173/login";
        String userUsername = apiUser.getUsername();
        String userName = apiUser.getName();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "Su nombre de usuario es: " + userUsername + "\r\n" + "Su nombre es: " + userName);
        mailSender.send(email);
    }
}
