package com.bingo.menu.command.impl;

import com.bingo.commons.pojo.identity.Admin;
import com.bingo.commons.pojo.identity.Role;
import com.bingo.commons.vo.ResultVO;
import com.bingo.menu.command.Command;
import com.bingo.menu.home.AdminHome;

/**
 * @author nia
 * @description 管理员命令
 * @Date 2024/6/6
 */
public class AdminHomeCommand implements Command {
    @Override
    public void execute(Role data) {
        ResultVO<Admin> resultVO = AdminHome.adminHome((Admin) data);
        System.out.println(resultVO.getMsg());
    }
}
