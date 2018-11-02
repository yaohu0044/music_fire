package com.musicfire.modular.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.utiles.Result;
import com.musicfire.modular.system.dto.RoleMenuDto;
import com.musicfire.modular.system.entity.RoleMenu;
import com.musicfire.modular.system.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {

    @Autowired
    private IRoleMenuService service;

    @GetMapping("/queryById/{id}")
    private Result queryByRoleId(@PathVariable Integer roleId ){

        RoleMenuDto dtos = service.queryByRoleId(roleId);

        return new Result().ok(dtos);
    }

    @GetMapping("/save")
    private Result save(@RequestBody RoleMenuDto dto){
        service.save(dto);
        return new Result().ok();
    }

}

