package com.aslanjavasky.shawarmadelviry.presentation.controller;

import com.aslanjavasky.shawarmadelviry.conf.AuthUtils;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.presentation.service.SessionInfoService;
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService;
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.UserDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users") //localhost:8081/users
public class UserController {

    private final UserService service;
    private final AuthUtils authUtils;
    private final SessionInfoService sessionInfoService;

    public UserController(UserService service, AuthUtils authUtils, SessionInfoService sessionInfoService) {
        this.service = service;
        this.authUtils = authUtils;
        this.sessionInfoService = sessionInfoService;
    }

    @GetMapping("/register")
    public String register(
            Model model
    ) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") UserDto user,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
//            log.info("ValidationErrors:"+result.getFieldError());
            model.addAttribute("user", user);
            return "register";
        }

        String encodedPassword = authUtils.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        service.createUser(user);
        log.info(String.valueOf(user));
        sessionInfoService.setUserInfo(user);
        log.info(String.valueOf(sessionInfoService));
        model.addAttribute("msg", "User registered successfully!");
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm(
            Model model
    ) {
        model.addAttribute("email", "");
        model.addAttribute("password", "");
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        try {
            IUser user = service.getUserByEmail(email);
            if (authUtils.authenticate(password, user.getPassword())) {
                sessionInfoService.setUserInfo(user);
                return "redirect:/menu";
            }
            model.addAttribute("error", "Invalid email or password");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Login failed:" + e.getMessage());
            return "login";
        }
    }


    @PostMapping("/delete")
    public String deleteUser(
            @RequestParam String email
    ) {

        IUser user = service.getUserByEmail(email);
        service.deleteUser(user);
        return "redirect:/users/register";


    }
}
