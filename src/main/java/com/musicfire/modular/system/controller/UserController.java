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

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;

    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class) @RequestBody UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        service.save(user);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody @Validated(value = Update.class) UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        service.save(user);
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

    @GetMapping("/queryByUserName/{name}")
    public Result list(@PathVariable String name) {
        User user = service.queryByUserName(name);
        return new Result().ok(user);
    }

}

