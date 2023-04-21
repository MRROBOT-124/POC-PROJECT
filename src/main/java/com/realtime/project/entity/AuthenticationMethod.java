package com.realtime.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realtime.project.constants.AuthenticationMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serializable;

/**
 * POJO CLASS USED TO REPRESENT CLIENT AUTHENTICATION
 * METHODS SEE THE @AuthenticationMethodEnum FOR THE
 * LIST OF AVAILABLE OPTIONS
 */
@Getter
@Setter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationMethod implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Enumerated(EnumType.STRING)
    private AuthenticationMethodEnum value;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Client.class, optional = false)
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private Client client;
}
