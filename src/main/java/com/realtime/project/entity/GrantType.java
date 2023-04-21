package com.realtime.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realtime.project.constants.AuthorizationGrantTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * POJO CLASS HOLDS THE GRANTS AVAILABLE
 * SEE @AuthorizationGrantTypeEnum FOR THE
 * LIST OF AVAILABLE OPTIONS
 */
@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class GrantType {

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
