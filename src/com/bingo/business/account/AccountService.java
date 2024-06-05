package com.bingo.business.account;

import com.bingo.business.management.Platform;
import com.bingo.commons.enums.RoleType;
import com.bingo.commons.exception.BingoException;
import com.bingo.commons.pojo.identity.*;
import com.bingo.commons.vo.ResultVO;

/**
 * @author nia
 * @description 登录服务
 * @Date 2024/6/4
 */
public class AccountService {

    //平台对象
    private static Platform platform = Platform.getInstance();

    //登录逻辑
    public static ResultVO<Role> login(String account, String pwd, RoleType roleType){
        Role role;
        switch (roleType){
            case PLATFORM_ADMIN -> //todo 从存储结构中拿取对应的类型数据
                //PlatformAdmin platformAdmin = getAdmin(account);
                    role = new PlatformAdmin();
            case MERCHANT -> role = new Merchant();
            case PURCHASER -> role = new Purchaser();
            default -> throw new BingoException("LoginService#login RoleType error");
        }
        if (pwd.equals(role.getPwd())) {
            return ResultVO.success("登录成功",role);
        }
        return ResultVO.fail(account +"登录失败");
    }

    //User注册逻辑
    public static  <T extends User> ResultVO<T> registerUser(String name,String account, String pwd,RoleType roleType){
        T user = platform.createRole(roleType);
        user.setAccount(account);
        user.setPwd(pwd);
        user.setName(name);
        //todo 存入存储结构中 其余元素为默认
        return ResultVO.success("创建用户成功！" + user);
    }

    //admin注册逻辑
    public static <T extends Admin> ResultVO<T> registerAdmin(String account, String pwd,RoleType roleType){
        T admin = platform.createRole(roleType);
        admin.setAccount(account);
        admin.setPwd(pwd);
        //todo 存入存储结构中 其余元素为默认
        return ResultVO.success("创建用户成功！" + admin);
    }


}
