package com.bingo.data.graph;

public class VNode {
    public int id; // 顶点 id
    public String info; // 顶点信息
    public ArcNode firstArc; // 第一个边结点

    //两种初始化方式
    public VNode(int id, String info) {
        this(id, info, null);
    }
    public VNode(int id, String info, ArcNode firstArc) {
        this.id = id;
        this.info = info;
        this.firstArc = firstArc;
    }

    //get and set
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public ArcNode getFirstArc() {
        return firstArc;
    }
    public void setFirstArc(ArcNode firstArc) {
        this.firstArc = firstArc;
    }
}