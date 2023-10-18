package com.equipo10.proyectointegrador.security;

import com.equipo10.proyectointegrador.dto.ApiUserDTO;
import com.equipo10.proyectointegrador.entity.ApiUser;
import com.equipo10.proyectointegrador.entity.ApiUserRoles;
import com.equipo10.proyectointegrador.entity.VerificationToken;
import com.equipo10.proyectointegrador.exceptions.ResourceAlreadyExistExeption;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.registration.OnRegistrationCompleteEvent;
import com.equipo10.proyectointegrador.registration.OnValidationCompleteEvent;
import com.equipo10.proyectointegrador.repository.ApiUserRepository;
import com.equipo10.proyectointegrador.repository.VerificationTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ApiUserServiceImpl implements UserDetailsService {
    @Autowired
    private ApiUserRepository apiUserRepository;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private JavaMailSender mailSender;

    public ApiUser insertUser(ApiUserDTO userDTO) throws ResourceAlreadyExistExeption {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ObjectMapper objectMapper = new ObjectMapper();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        ApiUser user = objectMapper.convertValue(userDTO, ApiUser.class);
        user.setApiUserRoles(ApiUserRoles.USER);
        if (emailExist(user.getEmail())) {
            throw new ResourceAlreadyExistExeption("Ya existe un usuario con ese email: " + user.getEmail());
        }
        return apiUserRepository.save(user);
    }

    public ResponseEntity<String> insertValidatedUser(String token) {
        VerificationToken verificationToken = getVerificationToken(token);
        if (verificationToken == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Su token ha expirado");
        }
        ApiUser apiUser = verificationToken.getApiUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Su token ha expirado");
        }
        apiUser.setEnabled(true);
        apiUserRepository.save(apiUser);
        eventPublisher.publishEvent(new OnValidationCompleteEvent(apiUser));
        return ResponseEntity.status(HttpStatus.OK).body("Usuario activado correctamente. \r\n Para ingresar a la pagina puede ingresar al siguiente link: http://127.0.0.1:5173/login");
    }

    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    public void createVerificationToken(ApiUser apiUser, String token) {
        VerificationToken myToken = new VerificationToken(apiUser, token);
        tokenRepository.save(myToken);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ApiUser> apiUser = apiUserRepository.findByEmail(email);
        if (apiUser.isEmpty()) {
            throw new UsernameNotFoundException("No existe un usuario con ese nombre");
        }
        return new User(apiUser.get().getEmail(), apiUser.get().getPassword(), apiUser.get().isEnabled(), apiUser.get().isAccountNonExpired(), apiUser.get().isCredentialsNonExpired(), apiUser.get().isAccountNonLocked(), apiUser.get().getAuthorities());
    }

    public Optional<ApiUserDTO> findByEmail(String email) throws UsernameNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiUserDTO apiUserDTO;
        ApiUser apiUser = null;
        Optional<ApiUser> optionalApiUserDTO = apiUserRepository.findByEmail(email);
        if (optionalApiUserDTO.isPresent()) {
            apiUser = optionalApiUserDTO.get();
        } else {
            throw new UsernameNotFoundException("no existe ese Email en la base de datos");
        }
        apiUserDTO = objectMapper.convertValue(apiUser, ApiUserDTO.class);
        return Optional.of(apiUserDTO);
    }


    public List<ApiUserDTO> searchAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ApiUserDTO> apiUserDTOList = new ArrayList<>();
        List<ApiUser> apiUserList = apiUserRepository.findAll();
        for (ApiUser apiUser : apiUserList
        ) {
            apiUserDTOList.add(objectMapper.convertValue(apiUser, ApiUserDTO.class));
        }
        return apiUserDTOList;
    }


    public void deleteUser(Integer id) {
        apiUserRepository.deleteById(id);
    }


    public void updateRole(ApiUserDTO apiUserDTO, Integer id) {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiUser apiUser1 = apiUserRepository.getReferenceById(id);
        ApiUser apiUser = objectMapper.convertValue(apiUserDTO, ApiUser.class);
        if (apiUser.getApiUserRoles() != null) {
            apiUser1.setApiUserRoles(apiUser.getApiUserRoles());
        }
        apiUserRepository.save(apiUser1);

    }

    public String resendRegistrationEmail(String appUrl, String email) throws ResourceNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiUser apiUser = apiUserRepository.findByEmail(email).orElse(null);
        if (apiUser == null) {
            throw new ResourceNotFoundException("El email no se encuentra registrado en la base de datos");
        }
        VerificationToken token = tokenRepository.findByApiUser(apiUser).orElse(null);
        if (token == null) {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(apiUser, appUrl));
        } else {
            mailSender.send(constructResendEmail(appUrl, token, apiUser));
        }
        return "Se reenvío el email de activación";
    }

    // UTILS

    private VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    private ApiUser getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getApiUser();
        }
        return null;
    }

    private boolean emailExist(String email) {
        return apiUserRepository.findByEmail(email).isPresent();
    }

    private SimpleMailMessage constructResendEmail(String contextPath, VerificationToken token, ApiUser apiUser) {
        String confirmationUrl = contextPath + "/users/registrationConfirm?token=" + token.getToken();
        String message = "Ingrese al siguiente link para activar su cuenta y empezar a utilizar nuestros servicios";
        return constructEmail(message + "\r\n" + confirmationUrl, apiUser);
    }

    private SimpleMailMessage constructEmail(String body, ApiUser apiUser) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Reenvío de email de activación de cuenta");
        email.setText(body);
        email.setTo(apiUser.getEmail());
        return email;
    }

}
