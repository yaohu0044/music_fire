package com.musicfire.modular.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.system.dto.RoleVo;
import com.musicfire.modular.system.entity.Role;
import com.musicfire.modular.system.query.RolePage;
import com.musicfire.modular.system.service.IRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService service;

    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class) @RequestBody RoleVo roleVo) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVo, role);
        List<Integer> menuIds = roleVo.getMenuIds();
        service.save(role,menuIds);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody @Validated(value = Update.class)RoleVo roleVo) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVo, role);
        service.save(role,roleVo.getMenuIds());
        return new Result().ok();
    }

    @GetMapping("/list")
    public Result list(RolePage rolePage) {
        RolePage page = service.list(rolePage);
        return new Result().ok(page);
    }

    @GetMapping("/delete/{ids}")
    public Result list(@PathVariable List<Integer> ids) {
        service.deleteByIds(ids);
        return new Result().ok();
    }
    @GetMapping("/queryByRoleName/{name}")
    public Result list(@PathVariable String name) {
        Role role = service.queryByRoleName(name);
        return new Result().ok(role);
    }
    @GetMapping("/getAll")
    public Result getAll() {
        EntityWrapper<Role> roleEntityWrapper = new EntityWrapper<>();
        roleEntityWrapper.eq("flag",false);
        List<Role> role = service.selectList(roleEntityWrapper);
        return new Result().ok(role);
    }
}

