package com.firs.wps.usermgt.security;

import com.firs.wps.usermgt.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

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