//package com.aslanjavasky.shawarmadelviry.security.controller;
//
//import com.aslanjavasky.shawarmadelviry.security.entity.LoginCredential;
//import com.aslanjavasky.shawarmadelviry.security.entity.UserSecurity;
//import com.aslanjavasky.shawarmadelviry.security.service.UserSecurityService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RequiredArgsConstructor

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

////@Controller
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final UserSecurityService userService;
//    private final PasswordEncoder passwordEncoder;
//
//

//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
//    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
//    @GetMapping("/register")
//    public String register(
//            Model model
//    ) {
//        model.addAttribute("user", new UserSecurity());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(
//            @Valid @ModelAttribute("user") UserSecurity user,
//            BindingResult result,
//            Model model
//    ) {
//        if (result.hasErrors()) {
//            model.addAttribute("user", user);
//            return "register";
//        }
//
//        userService.registerUser(user);
//        model.addAttribute("msg", "User registered successfully!");
//        return "redirect:/auth/login";
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm(
//            Model model
//    ) {
//        model.addAttribute("credential", new LoginCredential());
//        return "login";
//    }
//
////    @PostMapping("/login")
////    public String loginUser(
////            @Valid @ModelAttribute(name = "credential") LoginCredential credential,
////            BindingResult result,
////            Model model
////    ) {
////
////        if (result.hasErrors()) {
////            model.addAttribute("credential", credential);
////            return "login";
////        }
////
////        try {
////            userService.authenticate(credential);
////            return "redirect:/menu";
////        } catch (Exception e) {
////            model.addAttribute("error", "Login failed:" + e.getMessage());
////            return "login";
////        }
////    }
//
//
//}
