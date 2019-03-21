package com.musicfire.modular.order.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.order.dto.OrderExport;
import com.musicfire.modular.order.dto.OrderReport;
import com.musicfire.modular.order.dto.ReportParam;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;

import java.math.BigDecimal;
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
public interface IOrderService extends IService<Order> {

    OrderPage list(OrderPage orderPage);

    /**
     * 批量生产订单
     * @param ids 仓门信息
     */
    BigDecimal inserAll(List<Integer> ids, String unifiedNum,String userId,Integer payType);

    Map<String, Object> total(OrderPage page);

    /**
     * 根据条件生成底单信息
     * @param orderPage
     * @return
     */
    List<OrderExport> exportOrder(OrderPage orderPage);
}
