package com.equipo10.proyectointegrador.registration.listener;

import com.equipo10.proyectointegrador.entity.ApiUser;
import com.equipo10.proyectointegrador.registration.OnRegistrationCompleteEvent;
import com.equipo10.proyectointegrador.security.ApiUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private ApiUserServiceImpl apiUserService;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }


    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        ApiUser apiUser = event.getApiUser();
        String token = UUID.randomUUID().toString();
        apiUserService.createVerificationToken(apiUser, token);

        String recipientAddress = apiUser.getEmail();
        String subject = "Validación de registro";
        String confirmationUrl = event.getAppUrl() + "/users/registrationConfirm?token=" + token;
        String message = "Se registró correctamente. Para confirmar su registro, haga click en el siguiente enlace.";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}
