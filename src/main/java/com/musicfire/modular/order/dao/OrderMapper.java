package com.musicfire.modular.order.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.order.dto.OrderDto;
import com.musicfire.modular.order.dto.OrderReport;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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

    List<OrderDto> orderByPage(OrderPage orderPage);

    /**
     * 最近7天订单信息
     * @return 订单年月日和数量
     */
    List<Map<String,Integer>> orderReport();

    /**
     * 获取利润
     * @param commodityIds
     * @return
     */
    Integer profit(@Param("commodityIds") List<Integer> commodityIds);

    /**
     * 30天成交量
     * @param merchantId 商家Id
     * @return
     */
    List<Order> totalTo(Integer merchantId);

    /**
     *
     * @param page
     * @return
     */
    List<Map<String, String>> data(OrderPage page);

    /**
     * 根据条件统计订单信息
     * @param param
     * @return
     */
    List<OrderReport> orderReportCount(OrderPage param);

    /**
     * 当天支付订单统计
     * @param
     * @return
     */
    List<OrderReport> orderReportPaymentMethod();
}
