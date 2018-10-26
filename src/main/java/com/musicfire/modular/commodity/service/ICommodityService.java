package com.musicfire.modular.commodity.service;

import com.musicfire.modular.commodity.entity.Commodity;
import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface ICommodityService {

    String save(CommodityDto commodityDto);

    String edit(CommodityDto commodityDto);

    int commDeleteBatch(String ids);
}
