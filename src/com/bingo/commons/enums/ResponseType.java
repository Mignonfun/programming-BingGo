package com.bingo.commons.enums;

/**
 * @author nia
 * @description 响应枚举类
 * @Date 2024/6/4
 */
public enum ResponseType {

    SUCCESS("1","success"),
    FAIL("0", "fail"),
    ADMIN_LOGIN("L0001","管理员登录"),
    PURCHASER_LOGIN("L0002","用户登录"),
    MERCHANT_LOGIN("L0003","商家登录"),



    ;

    public final String code;

    public final String msg;

    ResponseType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
