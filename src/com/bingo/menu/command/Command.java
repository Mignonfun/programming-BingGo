package com.bingo.menu.command;

import com.bingo.commons.pojo.identity.Role;

/**
 * @author nia
 * @description 不同登录角色的命令接口
 * @Date 2024/6/6
 */
public interface Command {
    void execute(Role data);
}
