package com.musicfire.modular.order.service;

import com.musicfire.modular.order.entity.Order;
import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.order.page.OrderPage;

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
}
