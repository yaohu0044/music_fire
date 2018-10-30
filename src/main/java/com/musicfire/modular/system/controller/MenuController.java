package com.musicfire.modular.system.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.system.dto.MenuDto;
import com.musicfire.modular.system.dto.MenuVo;
import com.musicfire.modular.system.entity.Menu;
import com.musicfire.modular.system.query.MenuPage;
import com.musicfire.modular.system.service.IMenuService;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService service;

    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class) @RequestBody MenuVo menuVo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVo, menu);
        service.save(menu);
        return new Result().ok();
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody @Validated(value = Update.class)MenuVo menuVo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVo, menu);
        service.save(menu);
        return new Result().ok();
    }

    @PostMapping("/getMenuTree")
    public Result getMenuTree() {
        List<MenuDto> menuTree = service.getMenuTree();
        return new Result().ok(menuTree);
    }

    @GetMapping("/list")
    public Result list(MenuPage menuPage) {
        MenuPage page = service.list(menuPage);
        return new Result().ok(page);
    }
    @GetMapping("/delete/{id}")
    public Result list(@PathVariable Integer id) {
        service.deleteByIds(id);
        return new Result().ok();
    }
}

