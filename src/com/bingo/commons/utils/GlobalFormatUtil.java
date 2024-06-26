package com.bingo.commons.utils;

import com.bingo.commons.pojo.Goods;
import com.bingo.commons.pojo.identity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nia
 * @description 格式控制包
 * @Date 2024/6/5
 */
public class GlobalFormatUtil {
    //logo
    public static void logo(){
        System.out.println("========================================");
        System.out.println("\t  ____ ___ _   _  ____  ___ \t\t");
        System.out.println("\t | __ )_ _| \\ | |/ ___|/ _ \\ \t\t");
        System.out.println("\t |  _ \\| ||  \\| | |  _| | | | \t\t");
        System.out.println("\t | |_) | || |\\  | |_| | |_| | \t\t");
        System.out.println("\t |____/___|_| \\_|\\____|\\___/  v1.0.0\t");
        System.out.println("========================================");
    }

    public static void startWelcome(){
        System.out.println("=========== welcome to BinGo ===========");
    }

    public static void roleWelcome(String name){
        System.out.println("\t\t\t" + name +", 欢迎您");
    }

    public static void commands(List<String> commandlist){
        System.out.println("========================================");
        for (int i = 0; i < commandlist.size(); i++) {
            String line = "\t\t\t " + (i+1) + ". " + commandlist.get(i);
            System.out.println(line);
        }
        System.out.println("========================================");
    }

    public static void objectCommands(List<?> commandlist){
        System.out.println("========================================");
        for (int i = 0; i < commandlist.size(); i++) {
            String line = "\t\t\t " + (i+1) + ". " + commandlist.get(i).toString();
            System.out.println(line);
        }
        System.out.println("========================================");
    }

    public static void list(Map<String, Object> map){
        System.out.println("========================================");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String line = "\t\t " + entry.getKey() + " : " + entry.getValue().toString();
            System.out.println(line);
        }
        System.out.println("========================================");
    }

    public static String getNowDateString(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM--dd HH:mm:ss"));
    }

    public static void showGoods(List<Goods> allGoods,int start,int end) {
        System.out.println("  商品名称\t\t\t商品价格");
        for (int i = start; i < end; i++) {
            Goods goods = allGoods.get(i);
            System.out.printf("%d. %-17s %s\n",i+1,goods.getgName(),goods.getPrice());
        }
    }

}
