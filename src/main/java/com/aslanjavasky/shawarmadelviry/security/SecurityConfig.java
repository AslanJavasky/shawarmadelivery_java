package com.aslanjavasky.shawarmadelviry.security;

import com.aslanjavasky.shawarmadelviry.security.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.withUsername("Aslan@com")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .authorities("READ_PRIVILEGE")
                .build();

        UserDetails userTest = User.withUsername("test")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
                .authorities("READ_PRIVILEGE", "WRITE_PRIVILEGE")
                .build();


        return new InMemoryUserDetailsManager(user, userTest);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers("api/read/**").hasAuthority("READ_PRIVILEGE")
                                .requestMatchers("api/write/**").hasAuthority("WRITE_PRIVILEGE")
                                .anyRequest().authenticated())


//                .formLogin(form -> form
//                        .loginPage("/auth/login")
//                        .loginProcessingUrl("/auth/login")
//                        .defaultSuccessUrl("/menu", true)
//                        .failureUrl("/auth/login?error")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/auth/logout")
//                        .logoutSuccessUrl("/auth/login?logout")
//                        .permitAll()
//                );


                .httpBasic(Customizer.withDefaults());


        return http.build();
    }
}
