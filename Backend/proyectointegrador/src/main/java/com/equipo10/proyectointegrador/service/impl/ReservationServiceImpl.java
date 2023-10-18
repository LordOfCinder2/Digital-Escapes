package com.equipo10.proyectointegrador.service.impl;

import com.equipo10.proyectointegrador.dto.ReservationDTOBuilder;
import com.equipo10.proyectointegrador.entity.ApiUser;
import com.equipo10.proyectointegrador.entity.Experience;
import com.equipo10.proyectointegrador.entity.Reservation;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.model.DatePairs;
import com.equipo10.proyectointegrador.repository.ApiUserRepository;
import com.equipo10.proyectointegrador.repository.ExperienceRepository;
import com.equipo10.proyectointegrador.repository.ReservationRepository;
import com.equipo10.proyectointegrador.security.config.JwtTokenUtil;
import com.equipo10.proyectointegrador.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ApiUserRepository apiUserRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Integer postReservation(ReservationDTOBuilder reservationDTOBuilder, String token) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Reservation reservation = new Reservation();
        ApiUser apiUser = apiUserRepository.findById(decodeJWTToken(token)).orElse(null);
        Experience experience = experienceRepository.findById(reservationDTOBuilder.getExperienceId()).orElse(null);
        if (experience == null) {
            throw new ResourceNotFoundException("La experiencia no existe en la base de datos");
        }
        LocalDate departureDate = LocalDate.parse(reservationDTOBuilder.getDepartureDate());
        LocalDate returnDate = LocalDate.parse(reservationDTOBuilder.getReturnDate());
        reservation.setDepartureDate(departureDate);
        reservation.setReturnDate(returnDate);
        reservation.setExperience(experience);
        reservation.setApiUser(apiUser);
        reservationRepository.save(reservation);
        mailSender.send(constructEmail(reservation));
        return reservation.getId();
    }

    @Override
    public List<DatePairs> datesByExperience(Integer experienceId) {
        List<Reservation> reservationList = reservationRepository.reservationByExperience(experienceId);
        List<DatePairs> datePairsList = new ArrayList<>();
        for (Reservation reservation : reservationList
        ) {
            DatePairs datePairs = new DatePairs(reservation.getDepartureDate().toString(), reservation.getReturnDate().toString());
            datePairsList.add(datePairs);
        }
        return datePairsList;
    }


    // UTILS

    private Integer decodeJWTToken(String fullToken) throws Exception {
        String token = fullToken.substring(7);
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        System.out.println("userId: " + claims.get("userId"));
        Object userId = claims.get("userId");
        return (Integer) claims.get("userId");
    }


    private SimpleMailMessage constructEmail(Reservation reservation) {
        SimpleMailMessage email = new SimpleMailMessage();
        String body = "Gracias por utilizar Digital Escapes." + "\r\n" +
                "Reservaste una experiencia en: " + reservation.getExperience().getName() +
                ", tu fecha de inicio es el día: " + reservation.getDepartureDate().toString() +
                " hasta el día: " + reservation.getReturnDate().toString() + ".\r\n" +
                "Tu código de reserva es: " + reservation.getId() + ".\r\n" +
                "Por cualquier otra consulta podés ingresar a 127.0.0.1:5173 y te ayudaremos." + "\r\n" +
                "Gracias por elegirnos, esperamos que tengas una experiencia única!";
        email.setSubject("Confirmación de reserva");
        email.setText(body);
        email.setTo(reservation.getApiUser().getEmail());
        return email;
    }
}
