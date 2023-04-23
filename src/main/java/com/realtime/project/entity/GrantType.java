package com.realtime.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realtime.project.constants.AuthorizationGrantTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serializable;

/**
 * POJO CLASS HOLDS THE GRANTS AVAILABLE
 * SEE @AuthorizationGrantTypeEnum FOR THE
 * LIST OF AVAILABLE OPTIONS
 */
@Getter
@Setter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrantType implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Enumerated(EnumType.STRING)
    private AuthorizationGrantTypeEnum value;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Client.class, optional = false)
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Client client;

}
