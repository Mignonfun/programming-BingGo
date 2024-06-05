package com.bingo.commons.pojo.identity;

/**
 * @author nia
 * @description 商家店铺
 * @Date 2024/6/4
 */
public class Merchant extends User {
    private Integer mId;
    private Integer followers;

    private Double income;

    public Integer getMId() {
        return mId;
    }

    public void setMId(Integer mId) {
        this.mId = mId;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
}
