package com.musicfire.modular.system.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.system.dto.UserVo;
import com.musicfire.modular.system.entity.User;
import com.musicfire.modular.system.query.UserPage;
import com.musicfire.modular.system.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;

    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class) @RequestBody UserVo userVo) {
        return getResult(userVo);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody @Validated(value = Update.class) UserVo userVo) {
        return getResult(userVo);
    }

    private Result getResult(@Validated(Update.class) @RequestBody UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        List<Integer> roles = userVo.getRole();
        service.save(user,roles);
        return new Result().ok();
    }

    @GetMapping("/list")
    public Result list(UserPage userPage) {
        UserPage page = service.list(userPage);
        return new Result().ok(page);
    }

    @GetMapping("/delete/{ids}")
    public Result list(@PathVariable List<Integer> ids) {
        service.deleteByIds(ids);
        return new Result().ok();
    }

    @GetMapping("/queryByUserName")
    public Result queryByUserName(String name) {
        List<User> users = service.queryByUserName(name);
        return new Result().ok(users);
    }
    @GetMapping("/queryUserByName")
    public Result queryUserByName(String name) {
        List<User> users = service.queryUserByName(name);
        return new Result().ok(users);
    }
}

