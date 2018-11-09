package com.musicfire.modular.order.controller;


import com.musicfire.common.utiles.Result;
import com.musicfire.common.validated.Insert;
import com.musicfire.modular.order.dto.OrderVo;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.order.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public Result save(@Validated(value = Insert.class) @RequestBody OrderVo orderVo) {
        Order order = new Order();
        BeanUtils.copyProperties(orderVo, order);
        service.insert(order);
        return new Result().ok();
    }

    @GetMapping("/list")
    public Result list(OrderPage orderPage) {
        OrderPage page = service.list(orderPage);
        return new Result().ok(page);
    }
}

