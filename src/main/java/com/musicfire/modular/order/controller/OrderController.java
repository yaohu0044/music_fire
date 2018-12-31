package com.musicfire.modular.order.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.utiles.Result;
import com.musicfire.common.utiles.UUIDTool;
import com.musicfire.mobile.controller.AliPayController;
import com.musicfire.modular.machine.dto.MachinePositionDto;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.order.dto.OrderDto;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private IMachinePositionService machinePositionService;

    @Autowired
    private AliPayController aliPay;

    @GetMapping(value = "/saveOrder/{ids}")
    @ResponseBody
    public Result saveOrder(@PathVariable List<Integer> ids) {
        String unifiedNum = UUIDTool.getOrderIdByUUId();
        BigDecimal price = service.inserAll(ids, unifiedNum);
        List<MachinePositionDto> dto =machinePositionService.queryByIds(ids);

        OrderDto order = new OrderDto();
        order.setPrice(price);
        String s = dto.stream().map(m -> String.valueOf(m.getNum())).collect(Collectors.joining(","));
        order.setPositionNum(s);
        order.setUnifiedNum(unifiedNum);
        order.setCommodityName(dto.stream().map(MachinePositionDto::getCommodityName).collect(Collectors.joining(",")));
        order.setCommodityDes(dto.stream().map(MachinePositionDto::getCommodityDes).collect(Collectors.joining(",")));
//        order.setUnifiedNum(aliPay.aliPay(unifiedNum));
        return  new Result().ok(order);
    }

    @GetMapping("/list")
    public Result list(OrderPage orderPage) {
        OrderPage page = service.list(orderPage);
        return new Result().ok(page);
    }
}

