package com.bingo.menu.command.impl;

import com.bingo.commons.pojo.identity.Merchant;
import com.bingo.commons.pojo.identity.Role;
import com.bingo.commons.vo.ResultVO;
import com.bingo.menu.command.Command;
import com.bingo.menu.home.MerchantHome;

/**
 * @author nia
 * @description 商家命令
 * @Date 2024/6/6
 */
public class MerchantHomeCommand implements Command {
    @Override
    public void execute(Role data) {
        ResultVO<Merchant> resultVO = MerchantHome.merchantHome((Merchant) data);
        System.out.println(resultVO.getMsg());
    }
}
