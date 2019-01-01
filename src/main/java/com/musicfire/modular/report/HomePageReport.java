package com.musicfire.modular.report;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.utiles.DateTool;
import com.musicfire.common.utiles.Result;
import com.musicfire.modular.commodity.dao.CommodityMapper;
import com.musicfire.modular.commodity.entity.Commodity;
import com.musicfire.modular.merchant.dao.MerchantMapper;
import com.musicfire.modular.merchant.entity.Merchant;
import com.musicfire.modular.order.dao.OrderMapper;
import com.musicfire.modular.order.dto.OrderDto;
import com.musicfire.modular.order.dto.OrderReport;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class HomePageReport {

    @Resource
    private MerchantMapper merchantMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private CommodityMapper commodityMapper;

    @GetMapping("/homePage")
    public Result getHomePageReport(){
        EntityWrapper<Merchant> merchantEntityWrapper = new EntityWrapper<>();
        merchantEntityWrapper.eq("flag",false);
        int merchantCount = merchantMapper.selectCount(merchantEntityWrapper);
        EntityWrapper<Order> orderEntityWrapper = new EntityWrapper<>();
        int orderCount = orderMapper.selectCount(orderEntityWrapper);

        OrderPage orderPage = new OrderPage();
        List<OrderDto> orders = orderMapper.orderByPage(orderPage);

        List<Commodity> commodities = commodityMapper.queryHotCommodity();

        List<Map<String, Integer>> orderDay = orderMapper.orderReport();

        Map<String,Object> homePage = new HashMap<>();
        homePage.put("merchantCount",merchantCount);//商家数
        homePage.put("orderCount",orderCount);//订单数
        homePage.put("orders",orders);//最近订单
        homePage.put("commodities",commodities);//热门商品
        homePage.put("orderDay",orderDay);//最近7天订单和商品销售量

        return new Result().ok(homePage);
    }
    @GetMapping("/orderReport")
    public Result orderReport(OrderPage param) {
        if (param.getStartTime() == null) {
            param.setStartTime(DateTool.getYMD(DateTool.getBefDay(7)));
        }
        if (param.getEndTime() == null) {
            param.setEndTime(DateTool.getYMD(new Date()));
        }
        List<OrderReport> page = orderMapper.orderReportCount(param);
        return new Result().ok(page);
    }

    @GetMapping("/orderReportPaymentMethod")
    public Result orderReportPaymentMethod() {
        List<OrderReport> page = orderMapper.orderReportPaymentMethod();
        return new Result().ok(page);
    }

}
