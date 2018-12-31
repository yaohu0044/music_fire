package com.musicfire.modular.system.dao;

import com.musicfire.modular.system.dto.UserDto;
import com.musicfire.modular.system.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.system.query.UserPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    Integer countByPage(UserPage userPage);

    List<UserDto> userByPage(UserPage userPage);

    /**
     * 获取角色为商家的用户
     * @param name
     * @return
     */
    List<User> queryUserByName(String name);
}
