package com.aslanjavasky.shawarmadelviry.conf;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw.UserRepoImpl;
import com.aslanjavasky.shawarmadelviry.domain.repo.UserRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@ComponentScan(basePackages = "com.aslanjavasky.shawarmadelviry")
public class ShawarmaConfig {


    //    @Bean(name="URwAL")
//    @ConditionalOnBean(UserRepo.class)
//    public UserRepo userRepo(){
//        return new UserRepoImpl();
//    }

//    @Bean("URwLL")
////    @Primary
//    public UserRepo userRepoLinkedList(){
//        return new UserRepoImplWithLinkedList();
//    }

//    @Bean
//    public UserService userService(UserRepoImplWithLinkedList repo){
//        return new UserService(repo);
//    }
//
//    @Bean
//    public UserController userController(UserService userService){
//        return new UserController(userService);
//    }

//    @Bean
//    public CommandLineRunner commandlineRunner(UserController userController){
//        return new ApplicationStartupRunner(userController);
//    }

//    @Bean
//    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
//    public User user(){
//        User user=new User();
//        user.setName("Aslan Javasky");
//        user.setTelegram("@Aslan_Javasky");
//        return user;
//    }


}
