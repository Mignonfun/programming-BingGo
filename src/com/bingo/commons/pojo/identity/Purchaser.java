package com.bingo.commons.pojo.identity;

/**
 * @author nia
 * @description 用户
 * @Date 2024/6/4
 */
public class Purchaser extends User {
    /**
     * 用户id
     */
    private String pId;
    /**
     * 余额
     */
    private Double balance;

    /**
     * 收货地址
     */
    private String address;


    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAddresses() {
        return address;
    }

    public void setAddresses(String address) {
        this.address = address;
    }
}
