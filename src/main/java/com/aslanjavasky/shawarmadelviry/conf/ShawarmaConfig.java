package com.aslanjavasky.shawarmadelviry.conf;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw.UserRepoImpl;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw.UserRepoImplWithLinkedList;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import com.aslanjavasky.shawarmadelviry.presentation.service.UserController;
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ShawarmaConfig {

//    @Bean(value="URwAL")
    @Bean(name="URwAL")
    public UserRepo userRepo(){
        return new UserRepoImpl();
    }

    @Bean("URwLL")
//    @Primary
    public UserRepo userRepoLinkedList(){
        return new UserRepoImplWithLinkedList();
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepoLinkedList());
    }

    @Bean
    public UserController userController(){
        return new UserController(userService());
    }

    @Bean
    public CommandLineRunner commandlineRunner(){
        return new ApplicationStartupRunner(userController());
    }


}
