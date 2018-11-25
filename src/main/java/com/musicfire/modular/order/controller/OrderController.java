package com.musicfire.modular.order.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.common.utiles.UUIDTool;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService service;

    @GetMapping(value = "/saveOrder/{ids}")
    @ResponseBody
    public Result saveOrder(@PathVariable List<Integer> ids) {
        String unifiedNum = UUIDTool.getOrderIdByUUId();
        BigDecimal price = service.inserAll(ids, unifiedNum);
        Order order = new Order();
        order.setPrice(price);
        order.setUnifiedNum(unifiedNum);
        return  new Result().ok(order);
    }

    @GetMapping("/list")
    public Result list(OrderPage orderPage) {
        OrderPage page = service.list(orderPage);
        return new Result().ok(page);
    }
}

