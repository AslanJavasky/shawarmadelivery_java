package com.aslanjavasky.shawarmadelviry.presentation.controller;

import com.aslanjavasky.shawarmadelviry.conf.AuthUtils;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.presentation.service.SessionInfoService;
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService;
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.LoginCredential;
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

    private final UserService userService;
    private final AuthUtils authUtils;
    private final SessionInfoService sessionInfoService;

    public UserController(UserService userService, AuthUtils authUtils, SessionInfoService sessionInfoService) {
        this.userService = userService;
        this.authUtils = authUtils;
        this.sessionInfoService = sessionInfoService;
    }

    @GetMapping("/register")
    public String register(
            Model model
    ) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
//            log.info("ValidationErrors:"+result.getFieldError());
            model.addAttribute("userDto", userDto);
            return "register";
        }

        String encodedPassword = authUtils.encodePassword(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userService.createUser(userDto);
        sessionInfoService.setUserFields(userDto);

        log.info("userDto (POST registerUser)" + String.valueOf(userDto));
        log.info("sessionInfoService (POST registerUser)" + String.valueOf(sessionInfoService));
        model.addAttribute("msg", "User registered successfully!");
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm(
            Model model
    ) {
        model.addAttribute("credential", new LoginCredential());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @Valid @ModelAttribute(name = "credential") LoginCredential credential,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("user", credential);
            return "login";
        }

        try {
            IUser user = userService.getUserByEmail(credential.getEmail());
            if (authUtils.authenticate(credential.getPassword(), user.getPassword())) {
                if (sessionInfoService.getUsername() == null){
                    UserDto userDto=new UserDto();
                    userDto.setName(user.getName());
                    userDto.setPhone(user.getPhone());
                    userDto.setAddress(user.getAddress());
                    userDto.setEmail(user.getEmail());
                    userDto.setTelegram(user.getTelegram());
                    userDto.setPassword(user.getPassword());
                    sessionInfoService.setUserFields(userDto);
                }

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

//        IUser user = userService.getUserByEmail(email);
//        userService.deleteUser(user);
        userService.deleteUserByEmail(email);
        return "redirect:/users/register";
    }

//    @ModelAttribute(name="sessionInfoService")
//    public SessionInfoService sessionInfoService(){
//        return sessionInfoService;
//    }

}
