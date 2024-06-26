package com.bingo.commons.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nia
 * @description 购物车
 * @Date 2024/6/4
 */
public class Cart {
    /**
     * 用户id，用户与购物车一一对应
     */
    private String uid;
    /**
     * 总计数量
     */
    private Integer count;
    /**
     * 总计价格
     */
    private Double total;
    /**
     * 商品id列表
     */
    private List<Goods> goodsList;

    public Cart() {
        count = 0;
        total = 0.0;
        goodsList = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public void calcTotal() {
        total = goodsList.stream()
                .mapToDouble(Goods::getPrice)
                .sum();
    }
}
