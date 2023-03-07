package com.wjj.springsecurity2.domain;

public interface UserRepository {
    public MyUser findUserByUsername(String username);
}
