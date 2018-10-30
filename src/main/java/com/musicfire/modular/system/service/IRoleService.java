package com.musicfire.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.system.entity.Role;
import com.musicfire.modular.system.query.RolePage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface IRoleService extends IService<Role> {

    void save(Role role,List<Integer> menuIds);

    RolePage list(RolePage rolePage);

    void deleteByIds(List<Integer> ids);

    Role queryByRoleName(String name);
}
