package com.musicfire.modular.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.utiles.Result;
import com.musicfire.modular.system.entity.AliPaySetting;
import com.musicfire.modular.system.service.IAliPaySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2018-11-11
 */
@RestController
@RequestMapping("/aliPaySetting")
public class AliPaySettingController {
    @Autowired
    private IAliPaySettingService aliPaySettingService;

    @PostMapping("/save")
    private Result save(@RequestBody AliPaySetting aliPaySetting){
        aliPaySettingService.insert(aliPaySetting);
        return new Result().ok();
    }

    @GetMapping("/list")
    private Result list(){
        EntityWrapper<AliPaySetting> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderBy("id",false);
        return new Result().ok(aliPaySettingService.selectOne(entityWrapper));
    }
}

