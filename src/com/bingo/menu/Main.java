package com.bingo.menu;

import com.bingo.commons.constants.ListConstants;
import com.bingo.commons.pojo.identity.Role;
import com.bingo.commons.utils.GlobalFormatUtil;
import com.bingo.commons.utils.InputUtil;
import com.bingo.commons.vo.ResultVO;

import java.util.List;

/**
 * @author nia
 * @description 主面板
 * @Date 2024/6/5
 */
public class Main {
    public static ResultVO<Role> start(){
        List<String> start = ListConstants.START;

        while (true) {
            GlobalFormatUtil.logo();
            GlobalFormatUtil.welcome();
            GlobalFormatUtil.commands(start);
            int res = InputUtil.inputChar(start.size());

            switch (res){
                case 1 -> {
                    Role login = Login.login();
                    if (login == null){
                        continue;
                    }
                    return ResultVO.success();
                }
                case 2 -> Login.register();
                case 3 -> System.exit(1);
            }
        }
    }

    /**
     * 普通用户界面
     */
    public static void purchaserHome(){

    }
}
