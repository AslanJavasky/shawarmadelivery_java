package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users") //localhost:8081/users
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String register(
            Model model
    ) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute User user,
            Model model
    ) {
        log.info(String.valueOf(user));
        service.createUser(user);
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
            User user = service.getUserByEmail(email);
            if (user.getPassword().equals(password)) {
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

        User user = service.getUserByEmail(email);
        service.deleteUser(user);
        return "redirect:/users/register";


    }
}
