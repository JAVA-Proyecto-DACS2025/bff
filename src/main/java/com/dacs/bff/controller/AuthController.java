package com.dacs.bff.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/secure/auth")
public class AuthController {

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", jwt.getClaimAsString("preferred_username"));
            userInfo.put("email", jwt.getClaimAsString("email"));
            userInfo.put("name", jwt.getClaimAsString("name"));
            userInfo.put("sub", jwt.getClaimAsString("sub"));

            // 💥 CORRECCIÓN CRÍTICA: Extrayendo roles anidados de 'realm_access'
            // Spring no puede acceder a claims anidados con .getClaimAsStringList()
            if (jwt.hasClaim("realm_access")) {
                // Obtenemos el mapa 'realm_access'
                @SuppressWarnings("unchecked")
                Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
                
                // Si el mapa existe y contiene la clave 'roles', extraemos la lista
                if (realmAccess != null && realmAccess.containsKey("roles")) {
                    // El valor es una Collection<String> que contiene los roles de Keycloak
                    userInfo.put("roles", realmAccess.get("roles")); 
                }
            }
            
            return ResponseEntity.ok(userInfo);
        }
        
        return ResponseEntity.ok(Map.of("authenticated", false));
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return ResponseEntity.ok(Map.of("token", jwt.getTokenValue()));
        }
        
        return ResponseEntity.ok(Map.of("token", ""));
    }
}
