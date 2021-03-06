package com.musicfire.modular.order.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.utiles.*;
import com.musicfire.mobile.controller.AliPayController;
import com.musicfire.mobile.entity.AliPayUserInfo;
import com.musicfire.modular.machine.dto.ExcelMachine;
import com.musicfire.modular.machine.dto.MachinePositionDto;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.order.dto.OrderDto;
import com.musicfire.modular.order.dto.OrderExport;
import com.musicfire.modular.order.dto.OrderReport;
import com.musicfire.modular.order.dto.ReportParam;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.order.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
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

    @Value("${projectUrl.vendingmachine}")
    public String serverUrl;

    @GetMapping(value = "/saveOrder/{ids}")
    @ResponseBody
    public Result saveOrder(@PathVariable List<Integer> ids, HttpServletRequest request) {
        //根据Id检查是否已经无货
        List<MachinePositionDto>  machinePositionDTos=machinePositionService.queryByIds(ids);
        machinePositionDTos.forEach(dto->{
            if(!dto.getAvailable()){
                throw new BusinessException(ErrorCode.RE_SELECTION);
            }
        });

        String netAgent = IpUtil.getAgent(request);
        String userId="";
        Integer payType = 0;
        if (netAgent.contains("wechat")) {
            Object attribute = request.getSession().getAttribute(Constant.WECHAT_OPEN_ID);
            System.out.println(attribute);
            userId = (String) attribute;
            payType=2;
        } else if(netAgent.contains("alipay")) {
            AliPayUserInfo userInfo = (AliPayUserInfo)request.getSession().getAttribute(Constant.AI_PAY_USER);
            System.out.println(userInfo);
            userId = userInfo.getUserId();
            payType=1;
        }

        String unifiedNum = UUIDTool.getOrderIdByUUId();
        BigDecimal price = service.inserAll(ids, unifiedNum,userId,payType);
        List<MachinePositionDto> dto =machinePositionService.queryByIds(ids);
        OrderDto order = new OrderDto();
        order.setPrice(price);
        String s = dto.stream().map(m -> String.valueOf(m.getNum())).collect(Collectors.joining(","));
        order.setPositionNum(s);
        System.out.println("当前订单ID信息000："+unifiedNum);
        order.setUnifiedNum(unifiedNum);
        order.setCommodityName(dto.stream().map(MachinePositionDto::getCommodityName).collect(Collectors.joining(",")));
        order.setCommodityDes(dto.stream().map(MachinePositionDto::getCommodityDes).collect(Collectors.joining(",")));
        System.out.println("当前订单ID0000信息："+unifiedNum);
        System.err.println(JSON.toJSONString(order));
        return  new Result().ok(order);
    }

    @GetMapping("/list")
    public Result list(OrderPage orderPage) {
        OrderPage page = service.list(orderPage);
        return new Result().ok(page);
    }
    @GetMapping("/exportOrder")
    public Result exportOrder(OrderPage orderPage, HttpServletResponse response) throws IOException {
        List<OrderExport> orderExports = service.exportOrder(orderPage);
        String fileName = "订单信息" + System.currentTimeMillis() + ".xls";
        FileOutputStream out = new FileOutputStream(Conf.getValue("excelImportAddr")+"/"+fileName);
        ExcelUtil<OrderExport> util = new ExcelUtil<>(OrderExport.class);// 创建工具类.
        util.exportExcel(orderExports, "订单新", 65536, out);// 导出
        return new Result().ok(serverUrl+"/"+fileName);
    }
}

