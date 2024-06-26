package com.bingo.commons.vo;

import com.bingo.commons.enums.ResponseType;

/**
 * @author nia
 * @description 返回对象
 * @Date 2024/6/4
 */
public class ResultVO<T> {

    private String code;

    private String msg;

    private T data;

    private ResultVO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResultVO(ResponseType responseType,T data){
        this.code = responseType.getCode();
        this.msg = responseType.getMsg();
        this.data = data;
    }

    private ResultVO(ResponseType responseType){
        this.code = responseType.getCode();
        this.msg = responseType.getMsg();
        this.data = null;
    }


    public static<T> ResultVO<T> success(){
        return new ResultVO<>(ResponseType.SUCCESS);
    }

    public static<T> ResultVO<T> success(String msg,T data){
        return new ResultVO<>(ResponseType.SUCCESS,data);
    }
    public static<T> ResultVO<T> success(String code,String msg,T data){
        return new ResultVO<>(code,msg,data);
    }


    public static<T> ResultVO<T> success(String msg){
        return new ResultVO<>(ResponseType.SUCCESS.getCode(),msg);
    }

    public static<T> ResultVO<T> fail(){
        return new ResultVO<>(ResponseType.FAIL);
    }

    public static<T> ResultVO<T> fail(String msg){
        return new ResultVO<>(ResponseType.FAIL.getCode(),msg);
    }

//    public static ResultVO resp(ResponseType responseType){
//        return new ResultVO(responseType);
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "[" + code + "] :" + msg;
    }
}
