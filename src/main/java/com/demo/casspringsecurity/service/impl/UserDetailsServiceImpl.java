package com.demo.casspringsecurity.service.impl;

import com.demo.casspringsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    //TODO: use Redis store to store the user details(unique identifier) with phone number as key
    //TODO: Timeout configuration should be morethan X-OTP redis configuration

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.demo.casspringsecurity.entity.User identifier = userService.getUser(username);
        if(identifier == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new User(username, identifier.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
