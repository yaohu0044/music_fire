package com.musicfire.modular.system.dao;

import com.musicfire.modular.system.entity.RoleMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 批量插入
     * @param roleMenus
     */
    void insertAll(List<RoleMenu> roleMenus);
}
