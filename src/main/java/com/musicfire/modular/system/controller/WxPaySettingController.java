package com.musicfire.modular.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.utiles.Result;
import com.musicfire.modular.system.entity.WxPaySetting;
import com.musicfire.modular.system.service.IWxPaySettingService;
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
@RequestMapping("/wxPaySetting")
public class WxPaySettingController {

    @Autowired
    private IWxPaySettingService wxPaySettingService;

    @PostMapping("/save")
    private Result save(@RequestBody WxPaySetting wxPaySetting){
        wxPaySettingService.insert(wxPaySetting);
        return new Result().ok();
    }

    @GetMapping("/list")
    private Result list(){
        EntityWrapper<WxPaySetting> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderBy("id",false);
        return new Result().ok(wxPaySettingService.selectOne(entityWrapper));
    }

}

