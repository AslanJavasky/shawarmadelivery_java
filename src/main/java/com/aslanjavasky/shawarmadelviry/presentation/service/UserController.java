package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
//import java.util.logging.Logger;

//Clean architecture
//MVC-Model View Controller

@Slf4j
@Controller
@RequestMapping("/users") //localhost:8081/users
public class UserController {
    //Mapping - GET, POST
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/register") //localhost:8081/users/register
    public String register(
//            @PathVariable String name,
            @RequestParam("name") String username,
            Model model
    ){
        model.addAttribute("name",username);
        return "register";
    }


}
