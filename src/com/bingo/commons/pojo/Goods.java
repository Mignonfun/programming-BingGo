package com.bingo.commons.pojo;

/**
 * @author nia
 * @description 商品
 * @Date 2024/6/4
 */
public class Goods {
    private Integer gId;
    private String gName;
    private Double price;
    private Integer sId;

    public Integer getGId() {

        return gId;
    }

    public void setGId(Integer gId) {
        this.gId = gId;
    }

    public String getGName() {
        return gName;
    }

    public void setGName(String gName) {
        this.gName = gName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }
}
