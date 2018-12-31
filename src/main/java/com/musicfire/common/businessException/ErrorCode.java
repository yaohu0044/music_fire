package com.musicfire.common.businessException;

public enum ErrorCode {

    MENU_URL_IS_NULL(2001, "菜单地址不能为空"),
    MENU_PARENT_URL_IS_NULL(2002, "父级菜单为空时，不需要地址"),
    NOT_LOGGED_IN(1, "未登录，清登录"),
    LOGIN_NAME_OR_PASSWORD_ERR(1, "账号或者密码错误"),
    PAREN_MENU(2004, "此菜单下存在子级才能，不能删除"),
    ROLE_NAME_REPEAT(2005, "角色名重复"),
    IS_NOT_DATA(2006, "没有更多数据"),
    PASSWORD_ERR(2007,"加密错误"),
    POSITION_OVERRUN(2008, "机器仓位超限"),
    NO_AUTHORITY(2009, "该用户没有权限"),
    TOKEN_ERR(20010, "生产token错误"),
    PAY_ERR(20011, "生产支付信息错误"),
    PAY_AMOUNT_ERR(20012, "支付信息验证失败"),
    ORDER_VERIFICATION_ERR(20013,"订单信息错误"),
    MACHINE_EXIST(20014,"机器存在"),
    ROOM_EXIST(20015,"房间名重复"),
    LOGO_NAME_EXIST(20016,"登录明重复"),
    NOT_EXIST(20017,"信息不存在"),
    ;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}