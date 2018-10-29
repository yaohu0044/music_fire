package com.musicfire.modular.commodity.service;

import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
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

    void save(CommodityDto commodityDto);

    void edit(CommodityDto commodityDto);

    int commDeleteBatch(String ids);

    List<CommodityDto> queryList(RoomPage page);

    List<CommodityDto> queryCommodityByName(String name);
}
