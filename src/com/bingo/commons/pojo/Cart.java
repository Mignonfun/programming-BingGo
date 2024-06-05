package com.bingo.commons.pojo;

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
    private Integer uid;
    /**
     * 总计数量
     */
    private Integer count;
    /**
     * 总计价格
     */
    private Integer total;
    /**
     * 商品Map
     * 商品号与商品数量的映射
     */
    private Map<Integer,Integer> goodsMap;
    /**
     * 商品id列表
     */
    private List<Integer> gIdList;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Map<Integer, Integer> getGoodsMap() {
        return goodsMap;
    }

    public void setGoodsMap(Map<Integer, Integer> goodsMap) {
        this.goodsMap = goodsMap;
    }

    public List<Integer> getgIdList() {
        return gIdList;
    }

    public void setgIdList(List<Integer> gIdList) {
        this.gIdList = gIdList;
    }
}
