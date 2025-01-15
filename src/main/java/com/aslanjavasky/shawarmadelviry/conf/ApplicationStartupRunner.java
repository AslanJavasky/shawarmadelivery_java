package com.aslanjavasky.shawarmadelviry.conf;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.presentation.service.UserController;
import org.springframework.boot.CommandLineRunner;


public class ApplicationStartupRunner implements CommandLineRunner {

    private final UserController userController;

    public ApplicationStartupRunner(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void run(String... args) throws Exception {
        userController.createUser(new User());
        userController.deleteUser(new User());
    }
}
