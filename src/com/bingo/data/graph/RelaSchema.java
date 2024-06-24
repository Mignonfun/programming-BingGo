package com.bingo.data.graph;



import com.bingo.data.fileUtil.FileProcessor;

import java.util.LinkedList;

public class RelaSchema {
    FileProcessor filp =new FileProcessor();
    String path;        //对应json文件的地址
    ALGraph graph;      //json文件对应的图
    public RelaSchema(String path){
        this.path=path;
        graph=new ALGraph();
        if(filp.exist(path)) {              //如果已经有对应json文件存在
            graph = filp.getFromFileJson(path);
        }
    }
//    通过Node.id添加结点
    public boolean addNode(int id,String info){
        boolean success = graph.addNode(id, info);
        if(success){
            filp.saveToFileJson(graph,path);
        }
        return success;
    }
//    自增方式添加结点
    public boolean addNode(String info){
        boolean success = graph.addNode(info);
        if(success){
            filp.saveToFileJson(graph,path);
        }
        return success;
    }
//    根据b+树索引添加顶点
    public boolean addNode_byList(LinkedList<String> list){
        boolean success = graph.addNode_byList(list);
        if(success){
            filp.saveToFileJson(graph,path);
        }
        return success;
    }
//    根据node.id添加边
    public boolean addEdge(int u,int v){
        boolean success = graph.addEdge(u, v);
        if(success){
            filp.saveToFileJson(graph,path);
        }
        return success;
    }
//    根据info添加边
    public boolean addEdge(String uInfo,String vInfo){
        boolean success = graph.addEdge(uInfo, vInfo);
        if(success){
            filp.saveToFileJson(graph,path);
        }
        return success;
    }
//    根据node.id删除顶点
    public boolean removeNode(int u){
        boolean success = graph.removeNode(u);
        if(success){
            filp.saveToFileJson(graph,path);
        }
        return success;
    }
//    根据info删除顶点
    public boolean removeNode(String uInfo){
        int uIndex=graph.findNode(uInfo);
        if(uIndex<0) return false;
        if(uIndex<0||uIndex>=graph.vexNum)return false;
        int u=graph.vexs[uIndex].id;
        boolean success = graph.removeNode(u);
        if(success){
            filp.saveToFileJson(graph,path);
        }
        return success;
    }
//    根据node.id删除边
    public boolean removeEdge(int u,int v){
        boolean success = graph.removeEdge(u, v);
        if(success){
            filp.saveToFileJson(graph,path);
        }
        return success;
    }
//    根据node.id删除边
    public boolean removeEdge(String uInfo,String vInfo){
        int uIndex=graph.findNode(uInfo);
        int vIndex=graph.findNode(vInfo);
        if(uIndex<0 || vIndex<0) return false;
        int u=graph.vexs[uIndex].id;
        int v=graph.vexs[vIndex].id;
        boolean success = graph.removeEdge(u, v);
        if(success){
            filp.saveToFileJson(graph,path);
        }
        return success;
    }
//    根据node.id拿到一个顶点的所有边
    public LinkedList<String> getAllEdge(int u){
        return graph.getAllEdge(u);
    }
//    根据info拿到一个顶点的所有边
    public LinkedList<String> getAllEdge(String uInfo){
        int uIndex=graph.findNode(uInfo);
        int u=graph.vexs[uIndex].id;
        return graph.getAllEdge(u);
    }
//    显示邻接表
    public void showGraph(){
        graph.showGraph();
    }
}
