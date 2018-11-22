package com.musicfire.mobile.enums;

import lombok.Getter;

/**
 * Description:
 * CreateBy: Sommer Jiang
 * CreateTime: 2018-03-02  7:52
 */
@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    PARAM_ERROR(1, "参数不正确"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),

    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),

    ORDER_NOT_EXIST(12, "订单不存在"),

    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_UPDATE_FAIL(15, "订单更新失败"),

    ORDER_DETAIL_EMPTY(16, "订单详情为空"),

    ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),

    CART_EMPTY(18, "购物车为空"),

    ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),

    WECHAT_MP_ERROR(20, "微信公众账号方面错误"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信支付异步通知金额校验不通过"),

    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),

    ORDER_FINISH_SUCCESS(23, "订单完结成功"),

    PRODUCT_STATUS_ERROR(24, "商品状态不正确"),

    LOGIN_FAIL(25, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(26, "登出成功"),
    NOT_SIGN_IN(32, "未登陆"),

    SELLER_QUERY_ERROR(27, "商家查询错误"),

    MACHINE_COPY_ERROR(28, "设备复制出错"),

    ORDER_COPY_ERROR(29, "订单复制出错"),

    ORDER_QUERY_ERROR(29, "订单查询出错"),
    ROME_IS_NULL_E(30, "房间信息为空"),
    MACHINE_REPEAT(31, "该机器已经分配房间"),

    REPLENISHMENT_ERR(33, "该机器已经分配房间"),
    ORDER_STATE_SUCCESS(1,"成功"),
    ORDER_STATE_FAIL(2,"失败"),
    ORDER_STATE_UNPAID(3,"未支付"),
    ALI_PAY(1,"AiPay"),
    WE_CHAT_PAY(2,"WeChat"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    ResultEnum(Integer code) {
        this.code = code;
    }

}
