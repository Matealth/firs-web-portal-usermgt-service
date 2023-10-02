package com.firs.wps.usermgt.security;

import com.firs.wps.usermgt.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.net.URI;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Value("${app.allow.origin}")
    private URI allowOrigin;
    private static final String[] AUTH_WHITELIST = {
            "/actuator/**",
            "/health/**",
            "/documentation/**",
            "/swagger-resources/**",
            "/v3/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/password/forgot-password",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.addAllowedOriginPattern(allowOrigin.toString());
                    config.setAllowCredentials(true);
                    return config;
                }).and()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/user/**").hasAnyRole("ADMIN")
                        .requestMatchers("/user/create").hasAnyRole("ADMIN")
                        .requestMatchers("/user/modify-role").hasAnyRole("ADMIN")
                        .requestMatchers("/user/modify-access").hasAnyRole("ADMIN")
                        .requestMatchers("/password/reset-password").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/user/edit").hasAnyRole("USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

}