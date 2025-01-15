package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.interractor.UserInterractor;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class UserService extends UserInterractor implements ApplicationContextAware {

    private ApplicationContext ctx;
    private UserRepo repo;

    public UserService() {
        super(null);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
//        this.repo = ctx.getBean("userRepoLinkedList",UserRepo.class);
//        this.repo = ctx.getBean(UserRepo.class);
//        this.repo = (UserRepo) ctx.getBean("userRepoLinkedList");
        this.repo = ctx.getBean("URwLL", UserRepo.class);
        super.setRepo(repo);
    }


}
