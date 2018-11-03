package com.musicfire.modular.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.utiles.Result;
import com.musicfire.modular.system.entity.Setting;
import com.musicfire.modular.system.service.ISettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private ISettingService settingService;


    @PostMapping("/save")
    public Result save(@Validated @RequestBody Setting setting) {
        settingService.save(setting);
        return new Result().ok();
    }

    @GetMapping("/getAll")
    public Result getAll() {
        EntityWrapper<Setting> entityWrapper = new EntityWrapper<>();
        List<Setting> settings = settingService.selectList(entityWrapper);
        return new Result().ok(settings);
    }
}

