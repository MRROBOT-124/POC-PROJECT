package com.realtime.project.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.realtime.project.constants.HelperConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEntity implements Serializable {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString() + "_" + LocalDateTime.now();
    private String aggregatetype;
    private String aggregateid;
    private String type;
    @Column(columnDefinition = "TEXT")
    private String payload;
    @Builder.Default
    private final Instant timestamp = Instant.now();

    public static String convertJson(Client client) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode asJson = objectMapper.createObjectNode()
                .put("aggregatetype", HelperConstants.CLIENT)
                .put("aggregateid", client.getId() + "_" + client.getClientName())
                .put("type", HelperConstants.CLIENT)
                .put("clientId", client.getId())
                .put("clientIdIssuedAt", client.getClientIdIssuedAt().toString())
                .put("clientSecret", client.getClientSecret())
                .put("clientSecretExpiresAt", client.getClientSecretExpiresAt().toString())
                .put("clientName", client.getClientName())
                .put("redirectUris", client.getRedirectUris())
                .put("scopes", client.getScopes());
        ArrayNode clientAuthenticationMethods = asJson.putArray("clientAuthenticationMethods");
        client.getClientAuthenticationMethods().forEach(authenticationMethod -> {
            ObjectNode objectNode = objectMapper.createObjectNode()
                    .put("authenticationMethod", authenticationMethod.getValue().toString());
            clientAuthenticationMethods.add(objectNode);
        });
        ArrayNode authorizationGrantTypes = asJson.putArray("authorizationGrantTypes");
        client.getAuthorizationGrantTypes().forEach(grantType -> {
            ObjectNode objectNode = objectMapper.createObjectNode()
                    .put("grantType", grantType.getValue().toString());
            authorizationGrantTypes.add(objectNode);
        });

        return asJson.toPrettyString();
    }


}
