package com.bingo.menu.command;

import com.bingo.commons.enums.ResponseType;
import com.bingo.menu.command.impl.AdminHomeCommand;
import com.bingo.menu.command.impl.MerchantHomeCommand;
import com.bingo.menu.command.impl.PurchaserHomeCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nia
 * @description 命令模式上下文
 * @Date 2024/6/6
 */
public class CommandContext {

    private static Map<String,Command> commandMap = new HashMap<>();

    private static boolean isInitialized = false;

    /**
     * 初始化方法
     */
    private static void init(){
        if (isInitialized){
            return;
        }
        commandMap.put(ResponseType.ADMIN_LOGIN.getCode(),new AdminHomeCommand());
        commandMap.put(ResponseType.PURCHASER_LOGIN.getCode(),new PurchaserHomeCommand());
        commandMap.put(ResponseType.MERCHANT_LOGIN.getCode(),new MerchantHomeCommand());
        isInitialized = true;
    }

    /**
     * 获取对应的命令类
     * @param code
     * @return
     */
    public static Command getCommand(String code){
        init();
        return commandMap.get(code);
    }


}
