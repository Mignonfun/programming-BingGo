package com.bingo.commons.pojo.identity;

/**
 * @author nia
 * @description Role
 * @Date 2024/6/4
 */
public abstract class Role {
    protected String account;
    protected String pwd;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Role{" +
                "account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}