package com.bingo.menu.command.impl;

import com.bingo.commons.pojo.identity.Purchaser;
import com.bingo.commons.pojo.identity.Role;
import com.bingo.commons.vo.ResultVO;
import com.bingo.menu.command.Command;
import com.bingo.menu.home.PurchaserHome;

/**
 * @author nia
 * @description 用户命令
 * @Date 2024/6/6
 */
public class PurchaserHomeCommand implements Command {
    @Override
    public void execute(Role data) {
        ResultVO<Purchaser> resultVO = PurchaserHome.purchaserHome((Purchaser) data);
        System.out.println(resultVO.getMsg());
    }
}
