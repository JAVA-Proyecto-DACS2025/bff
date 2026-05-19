package com.dacs.bff.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakUserDto {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean emailVerified;
    private Long createdTimestamp;
    private Map<String, List<String>> attributes;

    // Campo para los roles
    private List<String> roles;

    @Data
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Create extends KeycloakUserDto {
        private List<CredentialRepresentation> credentials;
        // NO declarar roles aquí - usa el heredado del padre
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Update extends KeycloakUserDto {
        private List<CredentialRepresentation> credentials;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CredentialRepresentation {
        private String type = "password";
        private String value;
        private Boolean temporary = false;
    }
}