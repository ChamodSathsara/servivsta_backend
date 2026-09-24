package com.gestetner.servvista.Config;

import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_ROUTES = {
            "/api/auth/login",
            "/api/auth/refresh",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_ROUTES).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        .requestMatchers("/api/roles/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().denyAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                        .authenticationEntryPoint((request, response, exception) ->
                                writeProblem(response, HttpStatus.UNAUTHORIZED,
                                        "Unauthorized", "A valid bearer token is required"))
                        .accessDeniedHandler((request, response, exception) ->
                                writeProblem(response, HttpStatus.FORBIDDEN,
                                        "Forbidden", "You do not have permission to access this resource")));

        return http.build();
    }

    @Bean
    public SecretKey jwtSecretKey(@Value("${security.jwt.secret}") String secret) {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (secretBytes.length < 32) {
            throw new IllegalArgumentException("JWT_SECRET must contain at least 32 characters");
        }
        return new SecretKeySpec(secretBytes, "HmacSHA256");
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {
        return NimbusJwtEncoder.withSecretKey(jwtSecretKey).build();
    }

    @Bean
    public JwtDecoder jwtDecoder(
            SecretKey jwtSecretKey,
            @Value("${security.jwt.issuer}") String issuer) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(jwtSecretKey).build();
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<Jwt>(
                JwtValidators.createDefaultWithIssuer(issuer)));
        return decoder;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("roles");
        authoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return authenticationConverter;
    }

    private static void writeProblem(
            HttpServletResponse response,
            HttpStatus status,
            String title,
            String detail) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().printf(
                "{\"type\":\"about:blank\",\"title\":\"%s\",\"status\":%d,\"detail\":\"%s\"}",
                title, status.value(), detail);
    }
}
