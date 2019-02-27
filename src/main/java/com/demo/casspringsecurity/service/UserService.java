package com.demo.casspringsecurity.service;

import com.demo.casspringsecurity.entity.User;

import java.util.List;

public interface UserService {
    public boolean addUser(User user);
    public List<User> getAllUsers();
    public User getUser(String username);
}
