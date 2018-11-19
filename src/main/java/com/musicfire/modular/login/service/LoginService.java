package com.musicfire.modular.login.service;


import com.musicfire.modular.login.Login;

public interface LoginService{
    Login verifyLogin(String userName, String password);
}