package com.bingo.menu;

import com.bingo.business.account.AccountService;
import com.bingo.commons.constants.ListConstants;
import com.bingo.commons.enums.RoleType;
import com.bingo.commons.pojo.identity.Role;
import com.bingo.commons.pojo.identity.User;
import com.bingo.commons.utils.GlobalFormatUtil;
import com.bingo.commons.utils.InputUtil;
import com.bingo.commons.vo.ResultVO;

import java.util.List;

/**
 * @author nia
 * @description 登录界面
 * @Date 2024/6/5
 */
public class Login {

    public static <T extends Role> T login() {
        List<String> login = ListConstants.LOGIN;
        GlobalFormatUtil.commands(login);
        int inputChar = InputUtil.inputChar(login.size());
        RoleType roleType = null;
        switch (inputChar) {
            case 1 -> roleType = RoleType.PURCHASER;
            case 2 -> roleType = RoleType.MERCHANT;
            case 3 -> roleType = RoleType.PLATFORM_ADMIN;
            case 4 -> {
                return null;
            }
        }
        String account = InputUtil.inputString("请输入账号：");
        String pwd = InputUtil.inputString("请输入密码：");
        ResultVO<Role> resultVO = AccountService.login(account, pwd, roleType);
        return (T) resultVO.getData();
    }

    public static void register() {
        List<String> register = ListConstants.REGISTER;
        GlobalFormatUtil.commands(register);
        int inputChar = InputUtil.inputChar(register.size());

        RoleType roleType;

        switch (inputChar) {
            case 1 -> roleType = RoleType.PURCHASER;
            case 2 -> roleType = RoleType.MERCHANT;
            default -> {
                return;
            }
        }

        String name = inputName(roleType);
        String account = InputUtil.inputString("请输入账号：");
        String pwd;
        while (true) {
            pwd = InputUtil.inputString("请输入密码：");
            String confirm = InputUtil.inputString("请确认密码：");
            if (pwd.equals(confirm)) {
                break;
            }
            System.out.println("两次输入的密码不同，请重新输入");
        }

        ResultVO<User> resultVO = AccountService.registerUser(name, account, pwd, roleType);
        System.out.println(resultVO.getMsg());
    }

    private static String inputName(RoleType roleType) {
        String str;
        if (roleType == RoleType.PURCHASER){
            str = "用户名";
        }else if (roleType == RoleType.MERCHANT){
            str = "店铺名";
        }else {
            return null;
        }
        return InputUtil.inputString("请输入您的"+str+"：");
    }

}
