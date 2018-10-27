package com.musicfire.modular.commodity.dao;

import com.musicfire.modular.commodity.entity.Commodity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.room.query.RoomPage;
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
public interface CommodityMapper extends BaseMapper<Commodity> {

    List<Commodity> queryByCommodity(RoomPage page);
}
