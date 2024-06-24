package com.bingo.data.graph;

public class ArcNode {
    public int adjVex; // 边结点在顶点数组中的索引位置
    public ArcNode nextArc; // 下一个边结点

    //构造方法
    public ArcNode(int adjVex) {
        this.adjVex = adjVex;
        this.nextArc = null;
    }

    //get and set
    public int getAdjVex() {
        return adjVex;
    }
    public void setAdjVex(int adjVex) {
        this.adjVex = adjVex;
    }
    public ArcNode getNextArc() {
        return nextArc;
    }
    public void setNextArc(ArcNode nextArc) {
        this.nextArc = nextArc;
    }
}