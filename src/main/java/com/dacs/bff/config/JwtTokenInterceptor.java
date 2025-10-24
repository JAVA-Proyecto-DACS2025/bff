package com.dacs.bff.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenInterceptor.class);

    @Override
    public void apply(RequestTemplate template) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) authentication.getPrincipal();
                String tokenValue = jwt.getTokenValue();
                
                if (tokenValue != null && !tokenValue.isEmpty()) {
                    // Agregar el token JWT al header Authorization
                    template.header("Authorization", "Bearer " + tokenValue);
                    logger.debug("JWT token agregado al header Authorization para la petición: {}", 
                               template.url());
                } else {
                    logger.warn("Token JWT está vacío para la petición: {}", template.url());
                }
            } else {
                logger.debug("No hay autenticación JWT disponible para la petición: {}", template.url());
            }
        } catch (Exception e) {
            logger.error("Error al agregar JWT token a la petición {}: {}", 
                        template.url(), e.getMessage());
        }
    }
}
