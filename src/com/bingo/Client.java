package com.bingo;

import com.bingo.commons.pojo.identity.Role;
import com.bingo.commons.vo.ResultVO;
import com.bingo.menu.Main;

/**
 * @author nia
 * @description 客户端入口类
 * @Date 2024/6/5
 */
public class Client {

    public static void main(String[] args) {
        while (true){
            ResultVO<Role> resultVO = Main.start();
            if (resultVO.getData() == null){
                continue;
            }


        }
    }


}
