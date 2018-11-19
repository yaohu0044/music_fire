package com.musicfire.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.system.dto.RoleMenuDto;
import com.musicfire.modular.system.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     *  根据角色Id获取其菜单
     */
    RoleMenuDto queryByRoleId(Integer roleId);

    /**
     * 分配权限
     * @param dto 对象
     */
    void save(RoleMenuDto dto);

    /**
     * 批量插入角色权限关系
     * @param roleMenus
     */
    void insertList(List<RoleMenu> roleMenus);
}
