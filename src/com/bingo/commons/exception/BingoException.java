package com.bingo.commons.exception;

/**
 * @author nia
 * @description 自定义异常类
 * @Date 2024/6/4
 */
public class BingoException extends RuntimeException{
    public BingoException(String message) {
        super(message);
    }
    public BingoException(Exception e) {
        super(e);
    }
}
