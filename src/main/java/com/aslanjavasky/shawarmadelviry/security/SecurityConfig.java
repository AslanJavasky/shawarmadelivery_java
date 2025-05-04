package com.aslanjavasky.shawarmadelviry.security;

import com.aslanjavasky.shawarmadelviry.security.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    /*
    CREATE TABLE users(
	username VARCHAR(50) NOT NULL PRIMARY KEY,
	password VARCHAR(100) NOT NULL,
	enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities(
	username VARCHAR(50) NOT NULL,
	authority VARCHAR(50) NOT NULL,
	FOREIGN KEY (username) REFERENCES users(username)
);
     */
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public CommandLineRunner initUsers(JdbcUserDetailsManager manager) {
        return args -> {
            UserDetails user = User.withUsername("Aslan@com")
                    .password(passwordEncoder().encode("1234"))
                    .authorities("WRITE_PRIVILEGE", "READ_PRIVILEGE")
                    .build();
            if (!manager.userExists("Aslan@com")){
                manager.createUser(user);
            }
        };
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


//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User.withUsername("Aslan@com")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER")
//                .authorities("READ_PRIVILEGE")
//                .build();
//
//        UserDetails userTest = User.withUsername("test")
//                .password(passwordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .authorities("READ_PRIVILEGE", "WRITE_PRIVILEGE")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(user, userTest);
//    }
}
