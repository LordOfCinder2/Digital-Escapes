package com.equipo10.proyectointegrador.controllers;

import com.equipo10.proyectointegrador.dto.ApiUserDTO;
import com.equipo10.proyectointegrador.entity.ApiUser;
import com.equipo10.proyectointegrador.exceptions.ResourceAlreadyExistExeption;
import com.equipo10.proyectointegrador.exceptions.ResourceNotFoundException;
import com.equipo10.proyectointegrador.model.JwtRequest;
import com.equipo10.proyectointegrador.model.JwtResponse;
import com.equipo10.proyectointegrador.registration.OnRegistrationCompleteEvent;
import com.equipo10.proyectointegrador.security.ApiUserServiceImpl;
import com.equipo10.proyectointegrador.security.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ApiUserController {
    @Autowired
    private ApiUserServiceImpl apiUserService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/addNewUser")
    public ResponseEntity<String> insertUser(@RequestBody ApiUserDTO userDTO, HttpServletRequest request) throws ResourceAlreadyExistExeption {
        ApiUser registered = apiUserService.insertUser(userDTO);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, getAppUrl(request)));
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente");
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<String> confirmRegistration(WebRequest request, @RequestParam("token") String token) {
        return apiUserService.insertValidatedUser(token);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        UserDetails apiUserDetails = apiUserService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<ApiUserDTO> fullDetails = apiUserService.findByEmail(authenticationRequest.getUsername());
        String token = jwtTokenUtil.generateToken(apiUserDetails, fullDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/resendEmail")
    public ResponseEntity<String> resendRegistrationEmail(HttpServletRequest request, @RequestParam String email) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(apiUserService.resendRegistrationEmail(getAppUrl(request), email));
    }

    @GetMapping("/user")
    public Optional<ApiUserDTO> getUserByEmail(@RequestParam String email) {
        return apiUserService.findByEmail(email);
    }


    @GetMapping("/all")
    public List<ApiUserDTO> getAllExperienceHandler() {
        return apiUserService.searchAll();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Integer id) {
        apiUserService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado correctamente");
    }

    @PutMapping
    public ResponseEntity<String> updateRole(@RequestParam Integer id, @RequestBody ApiUserDTO apiUserDTO) {
        apiUserService.updateRole(apiUserDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario actualizado correctamente");
    }


    // UTILS
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
