package com.musicfire.modular.system.dao;

import com.musicfire.modular.system.entity.UserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2018-11-07
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     *
     * @param userId 用户Id
     * @param roles 角色集合
     */
    void insertAll(@Param("userId") Integer userId, @Param("roles") List<Integer> roles);
}
