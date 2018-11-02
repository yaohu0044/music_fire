package com.musicfire.modular.system.service;

import com.musicfire.modular.system.dto.RoleMenuDto;
import com.musicfire.modular.system.entity.RoleMenu;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Mapper;

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
     * @param dto
     */
    void save(RoleMenuDto dto);

}
