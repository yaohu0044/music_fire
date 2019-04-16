package com.musicfire.modular.commodity.service;

import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.entity.Dto.CommodityVo;
import com.musicfire.modular.commodity.query.CommodityPage;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据商品Ids 获取商品
     * @param commodityId 商品Id
     * @return 商品信息
     */
    List<CommodityDto> queryByIds(List<Integer> commodityId);

    /**
     * 根据仓位号获取商品图片和描述
     * @param machinePositionId 仓位号
     * @return
     */
    Map<String,Object> getCommodityUrlAndIntroduceContent(Long machinePositionId);
}
