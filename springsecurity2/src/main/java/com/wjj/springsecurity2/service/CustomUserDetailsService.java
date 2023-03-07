package com.wjj.springsecurity2.service;

import com.wjj.springsecurity2.domain.MyUser;
import com.wjj.springsecurity2.domain.MyUserDetails;
import com.wjj.springsecurity2.domain.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    //注入编写的UserRepository
    @Resource
    private UserRepository userRepository;


    //根据username查询user
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findUserByUsername(username);
        if(user == null){
            System.out.println("============================null");
            throw  new UsernameNotFoundException(username + " Not found");
        }
        System.out.println("============================ not null");
        //要返回一个UserDetails
        //这个东西就是前面实现的那个类
        //用来验证该user是否有效
        return new MyUserDetails(user);
}

}
