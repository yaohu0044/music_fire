package com.musicfire.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.system.dto.MenuDto;
import com.musicfire.modular.system.entity.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 添加/修改菜单
     * @param menu
     */
    void save(Menu menu);


    List<MenuDto> getMenuTree();
}
