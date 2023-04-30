package com.realtime.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * POJO CLASS THAT IS A REPRESENTATION OF THE DATABASE TABLE
 * HOLDS CLIENT INFORMATION IN POSTGRESQL DB
 */
@Getter
@Setter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

    private static final long serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString() + "_" + LocalDateTime.now();
    @Column(unique = true)
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "client",
            orphanRemoval = true, targetEntity = AuthenticationMethod.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    @Builder.Default
    private Set<AuthenticationMethod> clientAuthenticationMethods = new HashSet<>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "client",
            orphanRemoval = true, targetEntity = GrantType.class, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<GrantType> authorizationGrantTypes = new HashSet<>();
    private String redirectUris;
    private String scopes;


    /**
     * UTILITY METHODS FOR RELATIONSHIP MAPPING
     * @param authenticationMethod
     */
    public void setClientAuthenticationMethods(Set<AuthenticationMethod> authenticationMethod) {

        clientAuthenticationMethods.addAll(authenticationMethod);
        authenticationMethod.stream().forEach(i -> i.setClient(this));
    }
    public void setAuthorizationGrantTypes(Set<GrantType> grantTypes) {

        authorizationGrantTypes.addAll(grantTypes);
        grantTypes.stream().forEach(i -> i.setClient(this));
    }




}
