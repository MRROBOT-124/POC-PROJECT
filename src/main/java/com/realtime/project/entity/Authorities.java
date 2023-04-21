package com.realtime.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * POJO CLASS THAT IS A REPRESENTATION OF THE DATABASE TABLE
 * HOLDS USER AUTHORITY INFORMATION IN POSTGRESQL DB
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
@Builder
@AllArgsConstructor
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String authority;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = UserDetails.class, optional = false)
    @JoinColumn(referencedColumnName = "username")
    @JsonIgnore
    private UserDetails userDetails;

}
