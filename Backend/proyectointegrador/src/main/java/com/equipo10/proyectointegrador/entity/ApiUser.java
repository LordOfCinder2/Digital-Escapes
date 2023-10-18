package com.equipo10.proyectointegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
public class ApiUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String username;
    private Integer phone;
    private String email;
    private String password;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private ApiUserRoles apiUserRoles;

    @OneToMany(mappedBy = "apiUser")
    @JsonIgnore
    private Set<Reservation> reservationSet = new HashSet<>();

    public ApiUser(String name, String email, String password, ApiUserRoles apiUserRoles) {
        this.name = name;
        this.username = email;
        this.email = email;
        this.password = password;
        this.apiUserRoles = apiUserRoles;
        this.enabled = false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(apiUserRoles.name());
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
