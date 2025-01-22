package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
//import java.util.logging.Logger;

@Slf4j
@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
//        LoggerFactory.getLogger(UserController.class).info("UserController bean is created!");
        log.info("UserController bean is created!");
        this.service = service;
    }

    public User createUser(User user){
        return service.createUser(user);
    }

    public void deleteUser(User user){
        service.deleteUser(user);
    }
}
