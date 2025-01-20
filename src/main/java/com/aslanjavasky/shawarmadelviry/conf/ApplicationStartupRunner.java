package com.aslanjavasky.shawarmadelviry.conf;

import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.presentation.service.UserController;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class ApplicationStartupRunner implements CommandLineRunner, ApplicationContextAware {


    private final UserController userController;
    private ApplicationContext ctx;

    public ApplicationStartupRunner(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = ctx.getBean(User.class);
        User user2 = ctx.getBean(User.class);

        userController.createUser(user1);
        userController.deleteUser(user2);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
