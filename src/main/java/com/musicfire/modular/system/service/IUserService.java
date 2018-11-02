package com.musicfire.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.system.entity.User;
import com.musicfire.modular.system.query.UserPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface IUserService extends IService<User> {
    /**
     * 添加/修改菜单
     *
     * @param user
     */
    void save(User user);

    /**
     * 菜单分页信息
     *
     * @param userPage 分页对象
     * @return
     */
    UserPage list(UserPage userPage);

    void deleteByIds(List<Integer> id);

    User queryByUserName(String name);

}
