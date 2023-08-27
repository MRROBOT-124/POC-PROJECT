package com.realtime.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

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
public class Authorities implements Serializable {

    @Id
    @Builder.Default
    private String id = String.valueOf(UUID.randomUUID());
    private String authority;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = UserInfo.class, optional = false)
    @JoinColumn(referencedColumnName = "username")
    @JsonIgnore
    private UserInfo userDetails;

}
