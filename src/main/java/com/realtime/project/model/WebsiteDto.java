package com.realtime.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realtime.project.entity.Tags;
import com.realtime.project.entity.Website;
import com.realtime.project.entity.WebsiteInfo;
import jakarta.persistence.Column;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteDto {
    @JsonProperty("aggregatetype")
    private String aggregatetype;
    @JsonProperty("aggregateid")
    private String aggregateid;
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private String id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("websiteName")
    private String websiteName;
    @JsonProperty("creatorName")
    private String creatorName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("submittedAt")
    private String submittedAt;
    @JsonProperty("tags")
    private List<Tags> tags;
    @JsonProperty("images")
    private List<WebsiteInfo> images;

    public Website convert() {
        return Website.builder().id(id).websiteName(websiteName)
                .creatorName(creatorName)
                .description(description)
                .images(images)
                .submittedAt(Instant.parse(submittedAt))
                .tags(tags)
                .build();
    }


}
