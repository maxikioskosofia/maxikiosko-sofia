package com.ecommerce.ecommerce.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ecommerce.ecommerce.jwt.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/health/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/user/me").authenticated() // Permitir acceso autenticado a /user/me
                .requestMatchers("/user/all").hasRole("ADMIN") // Restringir /user/all a ADMIN
                .requestMatchers("/user/role").hasRole("ADMIN") // Restringir /user/role a ADMIN
                .requestMatchers(HttpMethod.DELETE,"/user/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/points/assign").hasAnyRole("STAFF", "ADMIN") // Asignar puntos (STAFF o ADMIN)
                .requestMatchers(HttpMethod.PUT, "/points/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/points/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/points").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/points/user/{userId}").access(new WebExpressionAuthorizationManager(
                    "hasRole('ADMIN') or hasRole('STAFF') or @securityService.isUser(#userId)")) // Ver puntos de un usuario
                .requestMatchers(HttpMethod.POST, "/prizes").hasRole("ADMIN") // Crear premios (solo ADMIN)
                .requestMatchers(HttpMethod.GET, "/prizes/**").permitAll() // Listar/obtener premios (todos)
                .requestMatchers(HttpMethod.PUT, "/prizes/**").hasRole("ADMIN") //
                .requestMatchers(HttpMethod.DELETE, "/prizes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/prizes/**").hasRole("ADMIN") //
                .requestMatchers(HttpMethod.POST, "/redeems").authenticated() // Canjear premios (autenticados)
                .requestMatchers(HttpMethod.GET, "/redeems").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/redeems/user/{userId}").access(new WebExpressionAuthorizationManager(
                    "hasRole('ADMIN') or hasRole('STAFF') or @securityService.isUser(#userId)")) // Ver canjes de un usuario
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173, https://maxikiosco-sofia.vercel.app/"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "PATCH" ,"POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
