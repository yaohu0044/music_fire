package com.musicfire.modular.business.controller;

import com.musicfire.common.utiles.Result;
import com.musicfire.modular.business.service.BusinessService;
import com.musicfire.modular.machine.query.MachinePositionPage;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.order.service.IOrderService;
import com.musicfire.modular.room.query.RoomPage;
import com.musicfire.modular.room.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private IRoomService service;

    @Autowired
    private IMachinePositionService machinePositionService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private IOrderService orderService;

    /**
     *  商户获取所有房间和机器
     * @param page
     * @return
     */
    @RequestMapping("/romeList")
    @ResponseBody
    public Result RomeAndMachineList(RoomPage page) {
        page.setPageSize(-1);
        RoomPage roomPage = service.queryByRoom(page);
        return new Result().ok(roomPage.getList());
    }

    /**
     *  商户获取所有房间和机器
     * @param machineId 机器Id
     * @return 机器仓位信息
     */
    @RequestMapping("/queryMachinePositionByMachineId/{machineId}")
    @ResponseBody
    public Result queryMachinePositionByMachineId(@PathVariable Integer machineId) {
        MachinePositionPage positionPage = new MachinePositionPage();
        positionPage.setMachineId(machineId);
        MachinePositionPage machinePositionPage = machinePositionService.queryByMachinePosition(positionPage);
        return new Result().ok(machinePositionPage.getList());
    }

    /**
     * 补货
     * @param machinePositionId 仓位Id
     * @return
     */
    @GetMapping("/replenishment")
    @ResponseBody
    public Result replenishment(Integer machinePositionId) {
        businessService.replenishment(machinePositionId);
        return new Result().ok();
    }
    /**
     * 全部补货
     * @param machineId 机器id
     * @return
     */
    @GetMapping("/replenishmentAll")
    @ResponseBody
    public Result replenishmentAll(Integer machineId) {
        businessService.replenishmentAll(machineId);
        //打开全部机器全部仓位
        return new Result().ok();
    }
    /**
     * 我的订单
     * @param  orderPage 订单分页
     * @return 订单信息
     */
    @GetMapping("/orderList")
    public Result list(OrderPage orderPage) {
        OrderPage page = orderService.list(orderPage);
        return new Result().ok(page);
    }
}
