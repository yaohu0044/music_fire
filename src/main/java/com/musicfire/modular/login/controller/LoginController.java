package com.musicfire.modular.login.controller;

import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.Result;
import com.musicfire.modular.login.Login;
import com.musicfire.modular.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisDao redisDao;

    /**
     *
     * @return 所具备的权限
     */
    @PostMapping("/login")
    private Result login(@RequestBody Login login){
        Login verifyLogin = loginService.verifyLogin(login.getUserName(),login.getPassword());
        return new Result().ok(verifyLogin);
    }
}
