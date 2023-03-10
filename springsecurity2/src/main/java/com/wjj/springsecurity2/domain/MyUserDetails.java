package com.wjj.springsecurity2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetails extends MyUser implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public MyUserDetails(MyUser user){
        super(user.getId(), user.getUsername(), user.getPassword());
    }

    @Override
    public String getUsername(){
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
