package com.borlok.mvcSpine.config;

import com.borlok.mvcSpine.model.UserType;
import com.borlok.mvcSpine.security.JwtTokenFilter;
import com.borlok.mvcSpine.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author Erofeevskiy Yuriy on 28.05.2024
 */

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;
    private static final String LOGIN_ENTRY_POINT = "/api/v1/auth/login/**";
    private static final String ACTIVATION_ENTRY_POINT = "/api/v1/auth/activate/**";
    private static final String ADMIN_ENTRY_POINT = "/api/v1/admin/**";
    private static final String TOKEN_BASED_ENTRY_POINT = "/api/**";
    private static final String[] SWAGGER_BASED_ENTRY_POINT = {"/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(SWAGGER_BASED_ENTRY_POINT).permitAll()
                        .requestMatchers(LOGIN_ENTRY_POINT).permitAll()
                        .requestMatchers(ACTIVATION_ENTRY_POINT).permitAll()
                        .requestMatchers(ADMIN_ENTRY_POINT).hasAnyRole(UserType.ADMINISTRATOR.name(), UserType.OWNER.name())
                        .requestMatchers(TOKEN_BASED_ENTRY_POINT).hasAnyRole(UserType.USER.name(), UserType.SPECIALIST.name(),
                                UserType.ADMINISTRATOR.name(), UserType.OWNER.name())
                        .anyRequest().authenticated())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
