package com.musicfire.modular.commodity.service;

import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.entity.Dto.CommodityVo;
import com.musicfire.modular.commodity.query.CommodityPage;
import com.musicfire.modular.room.query.RoomPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface ICommodityService {

    void save(CommodityVo commodityVo);

    void edit(CommodityVo commodityVo);

    void commDeleteBatch(List<Integer> ids);

    CommodityPage queryList(CommodityPage page);

    List<CommodityDto> queryCommodityByName(String name);
}
