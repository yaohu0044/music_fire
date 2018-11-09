package com.musicfire.modular.order.dao;

import com.musicfire.modular.order.entity.Order;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.system.entity.User;
import com.sun.org.apache.xpath.internal.operations.Or;
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
public interface OrderMapper extends BaseMapper<Order> {

    Integer countByPage(OrderPage orderPage);

    List<Order> orderByPage(OrderPage orderPage);
}
