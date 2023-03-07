package com.wjj.springsecurity2;

import com.wjj.springsecurity2.domain.MyUser;
import com.wjj.springsecurity2.domain.UserSearching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Springsecurity2Application {

    public static void main(String[] args) {
        SpringApplication.run(Springsecurity2Application.class, args);
    }

    @Bean
    UserSearching userSearching(){
        MyUser user = new MyUser(1L, "username", "{bcrypt}$2a$10$h/AJueu7Xt9yh3qYuAXtk.WZJ544Uc2kdOKlHu2qQzCh/A3rq46qm");
        Map<String, MyUser> myUserMap = new HashMap<>();
        myUserMap.put("username", user);
        return new UserSearching(myUserMap);
    }
}
