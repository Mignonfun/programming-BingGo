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
    private Integer pId;
    /**
     * 余额
     */
    private Double balance;


    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
