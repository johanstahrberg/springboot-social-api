package se.jensen.johan.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests(auth -> auth
                // Token + register ska vara öppna
                .requestMatchers(HttpMethod.POST, "/request-token").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()

                // Swagger ska vara öppet
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/index.html"
                ).permitAll()

                // Allt annat kräver JWT
                .anyRequest().authenticated()
        );

        // Stäng av klassisk login
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        // JWT Resource Server (kräver att du har JwtDecoder/JwtEncoder beans som tidigare)
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}