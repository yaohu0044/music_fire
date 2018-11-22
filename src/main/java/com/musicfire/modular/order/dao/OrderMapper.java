package com.musicfire.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Integer countByPage(OrderPage orderPage);

    List<Order> orderByPage(OrderPage orderPage);

    /**
     * 最近7天订单信息
     * @return 订单年月日和数量
     */
    List<Map<String,Integer>> orderReport();
}
