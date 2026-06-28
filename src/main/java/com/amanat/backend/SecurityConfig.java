package com.amanat.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

    @Autowired
    private GoogleOAuthSuccessHandler googleOAuthSuccessHandler;
// dashboard.html ko permit karo but with authentication check

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/",
                        "/index.html",
                        "/login.html",
                        "/signup.html",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/signup",
                        "/oauth2/**",
                        "/login/oauth2/**",
                        "/api/auth/**"
                ).permitAll()
                // ✅ dashboard.html require authentication
                .requestMatchers("/dashboard.html").authenticated()
                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                .loginPage("/index.html")
                .successHandler(googleOAuthSuccessHandler)
                .defaultSuccessUrl("/dashboard.html", true)
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index.html")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
