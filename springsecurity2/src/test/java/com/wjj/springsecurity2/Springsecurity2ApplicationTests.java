package com.wjj.springsecurity2;

import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.hierarchical.OpenTest4JAwareThrowableCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationServiceException;

import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootTest
class Springsecurity2ApplicationTests {

    @Resource
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        log.error("key1 = {}", valueOperations.get("key1"));
    }

    @Test
    void varifyToken() {
        //验证token的有效性
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6InRlc3QgdXNlcm5hbWUifQ.Sr21-L8xR0trhJdDE8aBNHvl_2BXkqZQRcpTTIaPJ37FxBfqeSFeGqvaWZ5CrLXkZVwW9rE9lPBuZ3wH3PZqnw";
        JWTSigner jwtSigner = JWTSignerUtil.hs512("testttttt".getBytes(StandardCharsets.UTF_8));
        Boolean verify1 = JWTUtil.verify(token, jwtSigner);
        log.info(verify1.toString());

        //取出token里的信息
        String username = (String)JWTUtil.parseToken(token).getPayload("username");
        log.info("username = {}", username);
    }

}
