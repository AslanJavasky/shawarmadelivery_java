package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    public User createUser(User user){
        return service.createUser(user);
    }

    public void deleteUser(User user){
        service.deleteUser(user);
    }
}
