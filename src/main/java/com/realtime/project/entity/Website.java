package com.realtime.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.realtime.project.config.WebsiteSequenceGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO CLASS USED TO REPRESENT WEBSITE
 */
@Getter
@Setter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Website implements Serializable {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
    type = WebsiteSequenceGenerator.class)
    private String id;
    private String websiteName;
    private String creatorName;
    private String description;
    @Builder.Default
    private Instant submittedAt = Instant.now();
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, targetEntity = Tags.class)
    @JoinTable(name = "website_tag",
    joinColumns = @JoinColumn(name = "website_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tags> tags = new ArrayList<>();
    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, targetEntity = WebsiteInfo.class, mappedBy = "website")
    private List<WebsiteInfo> images = new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = UserInfo.class, optional = true)
    @JoinColumn(referencedColumnName = "username")
    @JsonIgnore
    private UserInfo userDetails;

}
