package com.musicfire.modular.system.service;

import com.musicfire.modular.system.entity.UserRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-11-07
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 分配角色
     * @param id 用户Id
     * @param roles 角色集合
     */
    void insertAll(Integer id, List<Integer> roles);

}
