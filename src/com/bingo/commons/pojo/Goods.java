package com.bingo.commons.pojo;

/**
 * @author nia
 * @description 商品
 * @Date 2024/6/4
 */
public class Goods {
    private String gId;
    private String gName;
    private Double price;
    private String sId;

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Goods{" +
                "gId=" + gId +
                ", gName='" + gName + '\'' +
                ", price=" + price +
                ", sId=" + sId +
                '}';
    }

    public Goods(String gId, String sId, String gName, Double price) {
        this.gId = gId;
        this.gName = gName;
        this.price = price;
        this.sId = sId;
    }
}
