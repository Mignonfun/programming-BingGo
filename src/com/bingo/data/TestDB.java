package com.bingo.data;


import com.bingo.data.bpFileTree.Table;
import com.bingo.data.fileUtil.FileProcessor;
import com.bingo.data.graph.ALGraph;
import com.bingo.data.graph.RelaSchema;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class TestDB {
    public static void main(String[] args) throws IOException {
//        test01();
//        test02();
//        test03();
//        test04();
//        test05();
//        test06();
//        test07();
//        test08();
//        test09();
//        test10();
//        test11();
//        test12(new ALGraph());
//        test13();
    }
//    测试整体导入数据
    public static void test06() throws IOException {
        MyDB db = new MyDB("D:\\myDB");
        Table user = db.createTable("user");
        user.createIndex(0);
        user.createIndex(1);
        user.bulkImport("C:\\Users\\35335\\Desktop\\test.txt", 5);
    }
//    测试importByCSV
    public static void test07() throws IOException {
        MyDB db = new MyDB("D:\\myDB");
        Table user = db.createTable("user");
        user.importByCSV();
    }
//    测试find
    public static void test08() throws IOException {
        MyDB db = new MyDB("D:\\myDB");
        Table user = db.createTable("user");
//        通过id查询
        LinkedList<String> byIdx = user.findByIdx(0, "23");
        for(String s:byIdx){
            System.out.print(s+" ");
        }
        System.out.println();
//        通过name查询
        LinkedList<String> linming = user.findByIdx(1, "Linming");
        for(String s:linming){
            System.out.print(s+" ");
        }
        System.out.println();
    }
//    测试insert
    public static void test09() throws IOException {
        MyDB db = new MyDB("D:\\myDB");
        Table user = db.createTable("user");
        LinkedList<String> in=new LinkedList<>();
        in.add("025");
        in.add("DDD");
        in.add("female");
        in.add("008");
        in.add("liben");
        user.insert(in);
    }
//    测试remove
    public static void test10() throws IOException {
        MyDB db = new MyDB("D:\\myDB");
        Table user = db.createTable("user");
        LinkedList<String> in=new LinkedList<>();
        in.add("025");
        in.add("DDD");
        in.add("female");
        in.add("008");
        in.add("liben");
        if(user.removeByIdx(in,0)){
            System.out.println("good");
        }else{
            System.out.println("not good");
        }
    }
//    测试update
    public static void test11() throws IOException {
        MyDB db = new MyDB("D:\\myDB");
        Table user = db.createTable("user");
        LinkedList<String> in=new LinkedList<>();
        in.add("025");
        in.add("DdddD");
        in.add("female");
        in.add("008");
        in.add("liben");
        if(!user.updateByFristIdx(in)){
            System.out.println("not good");
        }else System.out.println("good");
    }
//    测试Graph
    public static void test12(ALGraph graph){
        graph.addNode(0,"A");
        graph.addNode(1,"B");
        graph.addNode(2,"C");
        graph.addNode(3,"D");
        graph.addNode(4,"E");
        graph.addNode(5,"F");
        graph.addNode(6,"G");

        graph.addEdge(0,1);
        graph.addEdge(0,3);
        graph.addEdge(0,4);

        graph.addEdge(1,2);
        graph.addEdge(1,6);

        graph.addEdge(3,5);
        graph.addEdge(3,6);

        graph.addEdge(4,5);

        graph.showGraph();
        graph.removeNode(3);
        System.out.println();
        graph.showGraph();

        FileProcessor filp=new FileProcessor();
    //    filp.saveToFileJson(graph,"D:\\myDB\\myjson");
        ALGraph fromFileJson = filp.getFromFileJson("D:\\myDB\\myjson");
        System.out.println("fromJson:");
        fromFileJson.showGraph();

    //        graph.addNode(11,"A");
    //        graph.addNode(12,"B");
    //        graph.addNode(13,"C");
    //        graph.addNode(14,"D");
    //        graph.addNode(15,"E");
    //
    //        graph.addEdge(11,12);
    //        graph.addEdge(11,14);
    //
    //        graph.addEdge(12,13);
    //        graph.addEdge(12,15);
    //
    //        graph.addEdge(13,14);
    //        graph.addEdge(13,15);
    }
//    测试RelaSchema
    public static void test13(){
        MyDB db = new MyDB("D:\\myDB");
        RelaSchema userToUser = db.createRelaSchema("userToUser");
//        userToUser
        userToUser.showGraph();
//        userToUser.addEdge("D","A");
//        userToUser.addEdge("D","C");
//        userToUser.addEdge("D","D");
//        userToUser.addEdge("D","B");
        userToUser.removeNode("D");
        userToUser.addNode("D");
        userToUser.showGraph();
    }










//    随机String
    public static String generateRandomString(int length) {//生成length长度的随机字符串
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }
//    随机int
    public static int generateRandomInt(int min, int max) {//生成min,max范围内的随机int
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
//    不重复の随机String
    public static LinkedList<String> UniqueRandomStrings(int num,int length){
        LinkedList<String> uniqueStrings = new LinkedList<>();
        Set<String> generatedStrings = new HashSet<>();
        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz";

        while (uniqueStrings.size() < num) {
            StringBuilder randomString = new StringBuilder();
            for (int i = 0; i < length; i++) {
                char randomChar = characters.charAt(random.nextInt(characters.length()));
                randomString.append(randomChar);
            }
            if (!generatedStrings.contains(randomString.toString())) {
                uniqueStrings.add(randomString.toString());
                generatedStrings.add(randomString.toString());
            }
        }

        return uniqueStrings;
    }
}