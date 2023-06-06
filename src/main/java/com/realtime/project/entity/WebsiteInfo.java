package com.realtime.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * POJO CLASS USED TO REPRESENT WEBSITE INFO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class WebsiteInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String images;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Website.class)
    @JoinColumn(referencedColumnName = "id")
    private Website website;
    @Builder.Default
    private Instant createdAt = Instant.now();
}
