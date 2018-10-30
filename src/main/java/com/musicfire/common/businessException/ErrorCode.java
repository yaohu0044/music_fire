package com.musicfire.common.businessException;

public enum ErrorCode {

    MENU_URL_IS_NULL(2001, "菜单地址不能为空"),
    MENU_PARENT_URL_IS_NULL(2002, "父级菜单为空时，不需要地址"),
    NOT_LOGGED_IN(1, "未登录，清登录"),
    PAREN_MENU(2004, "此菜单下存在子级才能，不能删除"),
    ROLE_NAME_REPEAT(2005, "角色名重复"),
    ARRAY_LENGTH_IS_NULL(2003, "父级菜单为空时，不需要地址");

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