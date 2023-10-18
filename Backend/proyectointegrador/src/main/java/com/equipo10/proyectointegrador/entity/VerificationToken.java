package com.equipo10.proyectointegrador.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class VerificationToken {
    private static final int EXPIRATION = 60 * 48;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    @OneToOne(targetEntity = ApiUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private ApiUser apiUser;
    private Date expiryDate;

    public VerificationToken(ApiUser apiUser, String token) {
        this.apiUser = apiUser;
        this.token = token;
        this.expiryDate = calcuateExpiryDate();
    }

    private Date calcuateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, VerificationToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calcuateExpiryDate();
    }
}
