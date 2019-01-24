package com.musicfire.modular.order.dto;

import com.musicfire.common.utiles.ExcelVOAttribute;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderExport {

//    @ExcelVOAttribute(name = "用户昵称", column = "A")
    private String userName;
//    @ExcelVOAttribute(name = "用户头像", column = "B")
    private String headImg;
    @ExcelVOAttribute(name = "商品名称", column = "A")
    private String commodityName;
    @ExcelVOAttribute(name = "订单号", column = "B")
    private String number;
    @ExcelVOAttribute(name = "订单状态", column = "C")
    private String stateStr;
    private Integer state;
    @ExcelVOAttribute(name = "支付方式", column = "D")
    private String paymentMethodStr;
    private Integer paymentMethod;
    @ExcelVOAttribute(name = "金额", column = "E")
    private BigDecimal price;
    @ExcelVOAttribute(name = "商家名称", column = "F")
    private String merchantName;
    @ExcelVOAttribute(name = "机器名称", column = "G")
    private String machineName;

    @ExcelVOAttribute(name = "仓位号", column = "H")
    private Integer num;

    @ExcelVOAttribute(name = "房间名", column = "I")
    private String roomName;
    @ExcelVOAttribute(name = "创建时间", column = "J")
    private String createTimeStr;
    private Date createTime;









}
