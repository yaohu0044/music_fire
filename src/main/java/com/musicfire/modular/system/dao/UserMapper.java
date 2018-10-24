package com.musicfire.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2018-10-24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
