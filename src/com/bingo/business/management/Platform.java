package com.bingo.business.management;

import com.bingo.business.management.factory.RoleFactory;
import com.bingo.commons.enums.RoleType;
import com.bingo.commons.exception.BingoException;
import com.bingo.commons.pojo.identity.Role;

/**
 * @author nia
 * @description 平台（唯一性）使用双重检查锁的单例模式
 * @Date 2024/6/4
 */
public class Platform {

    /**
     * 私有静态变量
     * 使用volatile修饰单例实例的作用：确保线程安全的同时保持高性能
     */
    private static volatile Platform platform;
    //私有构造函数，防止外部创建实例
    private Platform(){}

    //公有静态方法，提供获取实例的方法
    public static Platform getInstance(){
        if (platform == null) {
            synchronized (Platform.class) {
                if (platform == null) {
                    platform = new Platform();
                }
            }
        }
        return platform;
    }


    /**
     * 创建角色
     * @param roleType1 用户 & 管理员
     * @param roleType2 具体类型
     * @return role
     */
    public <T extends Role> T createRole(RoleType roleType){
        RoleFactory factory = RoleFactory.getInstance();
        return factory.createRole(roleType);
    }



}
