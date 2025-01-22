package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Lazy
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        System.out.println("UserController bean is created!");
        this.service = service;
    }

    public User createUser(User user){
        return service.createUser(user);
    }

    public void deleteUser(User user){
        service.deleteUser(user);
    }
}
