package com.demo.order.app.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class OrderSecurityConfiguration
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/h2-console/**"))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/h2-console/**").permitAll()

                        // UI
                        .requestMatchers(HttpMethod.GET, "/ui/orders/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/ui/orders/**").hasRole("ADMIN")

                        // API
                        .requestMatchers(HttpMethod.POST, "/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/orders/**").hasAnyRole("USER", "ADMIN")

                        // REST
                        .requestMatchers(HttpMethod.POST, "/api/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/orders/**")
                        .hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                // API Auth
                .httpBasic(Customizer.withDefaults())

                // Browser Login
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/ui/orders", true)
                        .permitAll()
                )

                .logout(logout -> logout.logoutSuccessUrl("/login?logout"))

                .headers(headers ->
                        headers.frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}


