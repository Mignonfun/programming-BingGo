package com.bingo.data;

import com.bingo.data.bpFileTree.Table;
import com.bingo.data.fileUtil.FileProcessor;
import com.bingo.data.graph.RelaSchema;

import java.io.File;

public class MyDB {
    public String root;
    FileProcessor filp=new FileProcessor();
    public MyDB(String relaRoot){
        File relativeFile = new File(relaRoot);                 //代表相对路径的file
        String absolutePath = relativeFile.getAbsolutePath();   // 获取相对路径的绝对路径
        this.root=absolutePath;
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
