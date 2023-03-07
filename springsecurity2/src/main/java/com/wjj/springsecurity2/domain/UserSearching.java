package com.wjj.springsecurity2.domain;

import java.util.Map;

public class UserSearching implements UserRepository{
    private Map<String, MyUser> usernameToUser;

    public UserSearching(Map<String, MyUser> usernameToUser){
        this.usernameToUser = usernameToUser;
    }

    @Override
    public MyUser findUserByUsername(String username) {
        return usernameToUser.get(username);
    }

}
