package com.realtime.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * POJO CLASS USED TO REPRESENT TAGS
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Tags implements Serializable {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString() + "_" + LocalDateTime.now();
    private String tagName;
    private String tagDescription;
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    @ManyToMany(mappedBy = "tags")
    private List<Website> websites = new ArrayList<>();

}
