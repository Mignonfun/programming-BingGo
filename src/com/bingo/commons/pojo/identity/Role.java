package com.bingo.commons.pojo.identity;

import java.time.LocalDateTime;

/**
 * @author nia
 * @description Role
 * @Date 2024/6/4
 */
public abstract class Role {
    protected String account;
    protected String pwd;

    /**
     * 创建时间
     */
    protected String createTime;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Role{" +
                "account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}