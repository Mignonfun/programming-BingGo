package com.bingo.commons.enums;

/**
 * @author nia
 * @description 响应枚举类
 * @Date 2024/6/4
 */
public enum ResponseType {

    SUCCESS("1","success"),
    FAIL("0", "fail"),




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
