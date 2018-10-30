package com.musicfire.modular.system.dao;

import com.musicfire.modular.system.entity.Menu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.system.query.MenuPage;
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
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> menuByPage(MenuPage menuPage);

    Integer countByPage(MenuPage menuPage);

}
