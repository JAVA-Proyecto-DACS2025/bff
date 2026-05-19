package com.dacs.bff.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	// Bean para configurar CORS globalmente. (No se modifica)
	// @Bean
	// public CorsConfigurationSource corsConfigurationSource() {
	// UrlBasedCorsConfigurationSource source = new
	// UrlBasedCorsConfigurationSource();
	// CorsConfiguration config = new CorsConfiguration();

	// config.setAllowCredentials(true);

	// config.setAllowedOriginPatterns(Arrays.asList(
	// "http://localhost:9001",
	// "http://localhost:4200",
	// "http://localhost:3000",
	// "https://dacs2025.local",
	// "https://*.dacs2025.local"
	// ));

	// config.setAllowedHeaders(Arrays.asList(
	// "Authorization",
	// "Content-Type",
	// "X-Requested-With",
	// "Accept",
	// "Origin",
	// "Access-Control-Request-Method",
	// "Access-Control-Request-Headers"
	// ));

	// config.setExposedHeaders(Arrays.asList(
	// "Access-Control-Allow-Origin",
	// "Access-Control-Allow-Credentials"
	// ));

	// config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
	// "OPTIONS", "PATCH"));

	// config.setMaxAge(3600L);

	// source.registerCorsConfiguration("/**", config);
	// return source;
	// }

	// PERMITIR DESDE CUALQUIER ORIGEN (Para usar desde Postman o frontends en otros
	// orígenes)
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(Arrays.asList("*")); // Use allowedOriginPatterns for wildcards
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setExposedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(false); // Must be false with "*"
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	// MÉTODO CORREGIDO para extraer los roles de Keycloak.
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

		// 1. Define el conversor que extraerá y prefijará los roles
		converter.setJwtGrantedAuthoritiesConverter(this::extractRolesFromKeycloak);

		// 2. Define qué claim usar como nombre principal (opcional, pero buena
		// práctica)
		converter.setPrincipalClaimName("preferred_username");

		return converter;
	}

	/**
	 * Función que extrae los roles anidados del claim 'realm_access.roles' de
	 * Keycloak
	 * y los transforma en autoridades de Spring Security (ej: ROLE_ROLE-A).
	 */
	@SuppressWarnings("unchecked")
	private Collection<GrantedAuthority> extractRolesFromKeycloak(Jwt jwt) {
		if (jwt.hasClaim("realm_access")) {
			// Obtiene el mapa 'realm_access'
			Object realmAccessClaim = jwt.getClaim("realm_access");
			if (realmAccessClaim instanceof java.util.Map) {
				java.util.Map<String, Object> realmAccess = (java.util.Map<String, Object>) realmAccessClaim;

				// Obtiene la lista de roles del campo 'roles'
				Object rolesClaim = realmAccess.get("roles");
				if (rolesClaim instanceof Collection) {
					Collection<String> roles = (Collection<String>) rolesClaim;
					
					// Mapea cada rol, añade el prefijo "ROLE_" y lo convierte a
					// SimpleGrantedAuthority
					return roles.stream()
							.map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // 💥 Aquí se mapea el prefijo
																						// "ROLE_"
							.collect(Collectors.toList());
				}
			}
		}
		// Si no se encuentran roles de realm, devuelve una lista vacía
		return java.util.Collections.emptyList();
	}

	// 
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// DEBUG: Imprimir header Authorization en cada request
		org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter loggingFilter = new org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter() {
			@Override
			protected Object getPreAuthenticatedPrincipal(jakarta.servlet.http.HttpServletRequest request) {
				System.out.println("[DEBUG] Authorization header: " + request.getHeader("Authorization"));
				return null;
			}
			@Override
			protected Object getPreAuthenticatedCredentials(jakarta.servlet.http.HttpServletRequest request) {
				return null;
			}
		};
		http.addFilterBefore(loggingFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

		// --- CONFIGURACIÓN ORIGINAL HABILITADA ---
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authz -> authz
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// Endpoints públicos para health checks y ping
				.requestMatchers("/metrics/health", "/metrics/info").permitAll()
				.requestMatchers("/actuator/**").permitAll()
				.requestMatchers("/error").permitAll()
				.requestMatchers("/ping", "/version").permitAll()
				.requestMatchers("/graphql", "/graphql/**").permitAll()
				.requestMatchers("/graphiql", "/graphiql/**").permitAll()
				.requestMatchers("/conectorping", "/backendping").permitAll()
                .requestMatchers("/turnos/generar-turnos").permitAll()
				// .requestMatchers("/cirugia/**").permitAll() //borrar despues
				// .requestMatchers("/pacientes/**").permitAll() //borrar despues
				// .requestMatchers("/personal/**").permitAll() //borrar despues
				// .requestMatchers("/quirofano/**").permitAll() //borrar despues
				// Endpoints que requieren autenticación
				.requestMatchers("/secure/**").authenticated()
				.requestMatchers("/alumno/**").authenticated()
				.requestMatchers("/items/**").authenticated()
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

		// --- CONFIGURACIÓN TEMPORAL: PERMITIR TODO ---
		// http
		// 	.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		// 	.csrf(csrf -> csrf.disable())
		// 	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		// 	.authorizeHttpRequests(authz -> authz
		// 		.anyRequest().permitAll());
		// return http.build();
	}
}