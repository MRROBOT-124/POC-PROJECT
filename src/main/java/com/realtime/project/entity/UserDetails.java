package com.realtime.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * POJO CLASS THAT IS A REPRESENTATION OF THE DATABASE TABLE
 * HOLDS USER INFORMATION IN POSTGRESQL DB
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
@Builder
@AllArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails, Serializable {

    @Id
    private String username;
    private String password;
    private String email;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            targetEntity = Authorities.class, mappedBy = "userDetails", orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<Authorities> authorities = new ArrayList<>();
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @Builder.Default
    private Instant createdAt = Instant.now();

    public List<Authorities> getAuthoritiesList() {
        return this.authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(i -> new SimpleGrantedAuthority(i.getAuthority())).collect(Collectors.toSet());
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = new ArrayList<>();
        this.authorities.addAll(authorities);
        authorities.stream().forEach(authority -> authority.setUserDetails(this));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
