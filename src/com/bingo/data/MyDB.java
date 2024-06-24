package com.bingo.data;


import com.bingo.data.bpFileTree.Table;
import com.bingo.data.fileUtil.FileProcessor;
import com.bingo.data.graph.RelaSchema;

public class MyDB {
    public String root;
    FileProcessor filp=new FileProcessor();
    public MyDB(String root){
        this.root=root;
//        不存在，则创建
        if(!filp.exist(root))
            filp.createFolder(root);
        if(!filp.exist(root+"\\RelationGraph"))
            filp.createFolder(root);
    }
//    创建表
    public Table createTable(String tableName){
        Table table = new Table(root+"\\"+tableName);
        return table;
    }
//    创建图
    public RelaSchema createRelaSchema(String schName){
        filp.createFolder(root+"\\RelaSchemas");
        RelaSchema relaSchema=new RelaSchema(root+"\\RelaSchemas\\"+schName);
        return relaSchema;
    }
}
