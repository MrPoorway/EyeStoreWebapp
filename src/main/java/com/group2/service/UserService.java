package com.group2.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.group2.model.StoreException;
import com.group2.model.User;
import com.group2.model.UserRole;

public interface UserService {

    public User login(UserRole role, String email, String password, HttpSession session) throws StoreException;

    public String register(UserRole role, User user) throws StoreException;

    public boolean isLoggedIn(UserRole role, HttpSession session);

    public boolean logout(HttpSession session);
    
    public List<User> getAllUser() throws StoreException;

}
