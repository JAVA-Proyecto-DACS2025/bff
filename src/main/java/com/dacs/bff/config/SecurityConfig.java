package com.dacs.bff.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	// Bean para configurar CORS globalmente.
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		// Permite credenciales para el frontend Angular
		config.setAllowCredentials(true);
		
		// Orígenes permitidos - incluye localhost para desarrollo, QA y el dominio de producción
		config.setAllowedOriginPatterns(Arrays.asList(
			"http://localhost:9001",
			"http://localhost:4200",
			"http://localhost:3000",
			"https://dacs2025.local",
			"https://*.dacs2025.local"
		));
		
		// Headers permitidos
		config.setAllowedHeaders(Arrays.asList(
			"Authorization",
			"Content-Type",
			"X-Requested-With",
			"Accept",
			"Origin",
			"Access-Control-Request-Method",
			"Access-Control-Request-Headers"
		));
		
		// Headers expuestos al cliente
		config.setExposedHeaders(Arrays.asList(
			"Access-Control-Allow-Origin",
			"Access-Control-Allow-Credentials"
		));
		
		// Métodos HTTP permitidos
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
		
		// Tiempo de vida del preflight request
		config.setMaxAge(3600L);
		
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		
		// Configurar el extractor de authorities desde el JWT
		JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
		authoritiesConverter.setAuthorityPrefix("ROLE_");
		authoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
		
		converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
		converter.setPrincipalClaimName("preferred_username");
		
		return converter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authz -> authz
				// Endpoints públicos para health checks y ping
				.requestMatchers("/metrics/health", "/metrics/info").permitAll()
				.requestMatchers("/actuator/**").permitAll()
				.requestMatchers("/error").permitAll()
				.requestMatchers("/ping", "/version").permitAll()
				.requestMatchers("/conectorping", "/backendping").permitAll()
				
				// Endpoints que requieren autenticación
				.requestMatchers("/secure/**").authenticated()
				.requestMatchers("/alumno/**").authenticated()  // Requiere ROLE_A
				.requestMatchers("/items/**").authenticated()   // Requiere ROLE_B
				
				// Cualquier otra petición requiere autenticación
				.anyRequest().authenticated()
			)
			.oauth2ResourceServer(oauth2 -> oauth2
				.jwt(jwt -> jwt
					.jwtAuthenticationConverter(jwtAuthenticationConverter())
				)
				// Configurar manejo de errores de autenticación
				.authenticationEntryPoint((request, response, authException) -> {
					response.setStatus(401);
					response.setContentType("application/json");
					response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"Token JWT requerido o inválido\"}");
				})
			);

		return http.build();
	}
}

