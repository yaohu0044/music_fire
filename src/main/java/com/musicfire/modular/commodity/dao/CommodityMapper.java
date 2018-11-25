package com.musicfire.modular.commodity.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.commodity.entity.Commodity;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.query.CommodityPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    List<CommodityDto> queryByCommodity(CommodityPage page);
    Integer queryCount(CommodityPage page);

    /**
     * 热门商品前10条
     * @return 热门商品
     */
    List<Commodity> queryHotCommodity();

    List<CommodityDto> queryByIds(@Param("ids") List<Integer> commodityId);
}
