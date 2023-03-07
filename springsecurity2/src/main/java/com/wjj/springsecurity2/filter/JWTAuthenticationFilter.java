package com.wjj.springsecurity2.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.wjj.springsecurity2.domain.MyUser;
import com.wjj.springsecurity2.domain.MyUserDetails;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("path:{}", request.getRequestURI());
        //如果是要登录，放行到登录页面
        if("/login2".equals(request.getRequestURI())){
            filterChain.doFilter(request, response);
            return;
        }
        //不是进行登录，会携带token
        String token = request.getHeader("Authorization");
        if(StrUtil.isEmpty(token))
            throw new RuntimeException("token 为空");
        //token类型：postman前端传来的token会在前面加一个类型和一个空格
        if(!StrUtil.startWith(token, "Bearer"))
            throw new RuntimeException("token 类型错误");
        //验证token
        token = token.substring(7);
        JWTSigner jwtSigner = JWTSignerUtil.hs512("testttttt".getBytes(StandardCharsets.UTF_8));
        if (!JWTUtil.verify(token, jwtSigner)) {
            throw new RuntimeException("token 无效");
        }
        //从token中取出用户名
        String username = (String) JWTUtil.parseToken(token).getPayload().getClaim("username");

        //根据用户名从redis中取出password(登录时可以将UserDetails存储到redis)
        String password = (String) redisTemplate.opsForHash().get("user-details", username);
        if(password == null) throw new RemoteException("token 过期");
        log.info("--------> get userDetails = {}", password);

        //new一个UserDetails，这个UserDetails会被存储到SecurityContextHolder中
        MyUserDetails userDetails = new MyUserDetails(new MyUser(1, "username", "password"));

        //将UserDetails存储到SecurityContextHolder中
        SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(userDetails
        , null, null));
        filterChain.doFilter(request, response);
    }
}
