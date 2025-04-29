//package com.aslanjavasky.shawarmadelviry.security.service;
//
//import com.aslanjavasky.shawarmadelviry.presentation.service.dto.LoginCredential;
//import com.aslanjavasky.shawarmadelviry.security.entity.Role;
//import com.aslanjavasky.shawarmadelviry.security.entity.UserSecurity;
//import com.aslanjavasky.shawarmadelviry.security.repo.UserSecurityRepository;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
////@Service
//@RequiredArgsConstructor
//public class UserSecurityService {//implements UserDetailsService {
//
//    private final UserSecurityRepository userRepo;
//    private final PasswordEncoder passwordEncoder;
//
////    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserSecurity user = userRepo.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        return new User(
//                user.getUsername(),
//                user.getPassword(),
//                List.of(new SimpleGrantedAuthority(Role.USER.name())));
//    }
//
//    public UserSecurity registerUser(UserSecurity user) {
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//        user.setUsername(user.getEmail());
//
//        return userRepo.save(user);
//    }
//
//    public void authenticate(LoginCredential credential) {
//        UserDetails user = loadUserByUsername(credential.getEmail());
//        if (!passwordEncoder.matches(credential.getPassword(), user.getPassword())) {
//            throw new BadCredentialsException("Invalid credentials!");
//        }
//    }
//}
