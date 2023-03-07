package com.wjj.springsecurity2.controller;

import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.wjj.springsecurity2.domain.MyUserDetails;
import com.wjj.springsecurity2.util.LoginParams;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class controller1 {

    @GetMapping("/index2")
    public String index(){

        return "hello";
    }

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    AuthenticationManager authenticationManager;

    @PostMapping("/login2")
    public String login(@ModelAttribute LoginParams loginParams){

        //1. 创建一个UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginParams.getUsername(), loginParams.getPassword());

        //2. 使用authenticationManager进行认证
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //3. 检查是否认证成功:null就是不成功
        if(authentication == null) return "failed";

        //使用jwt工具生成token
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", userDetails.getUsername());
        JWTSigner jwtSigner = JWTSignerUtil.hs512("testttttt".getBytes(StandardCharsets.UTF_8));
        String token = JWTUtil.createToken(payload, jwtSigner);
        redisTemplate.opsForHash().put("user-details", userDetails.getUsername(), userDetails.getPassword());
        log.info("stored user-details: {}", userDetails);
        return token;
    }
}
