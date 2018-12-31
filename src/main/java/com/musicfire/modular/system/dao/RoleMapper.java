package com.musicfire.modular.system.dao;

import com.musicfire.modular.system.dto.RoleDTo;
import com.musicfire.modular.system.entity.Role;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.system.query.RolePage;
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
public interface RoleMapper extends BaseMapper<Role> {

    Integer countByPage(RolePage rolePage);

    List<RoleDTo> queryByPage(RolePage rolePage);
}
