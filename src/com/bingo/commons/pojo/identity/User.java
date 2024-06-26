package com.bingo.commons.pojo.identity;

/**
 * @author nia
 * @description 用户接口
 * @Date 2024/6/4
 */
public abstract class User extends Role{

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}