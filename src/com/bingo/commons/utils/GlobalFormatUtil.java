package com.bingo.commons.utils;

import java.util.List;

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

    public static void welcome(){
        System.out.println("=========== welcome to BinGo ===========");
    }

    public static void commands(List<String> commandlist){
        System.out.println("========================================");
        for (int i = 0; i < commandlist.size(); i++) {
            String line = "\t\t\t " + (i+1) + ". " + commandlist.get(i);
            System.out.println(line);
        }
        System.out.println("========================================");
    }
}
