package com.bingo.commons.enums;

import com.bingo.commons.pojo.identity.*;

/**
 * @author nia
 * @description 角色枚举
 * @Date 2024/6/4
 */
public enum RoleType {
    MERCHANT(Merchant.class),
    PLATFORM_ADMIN(PlatformAdmin.class),
    PURCHASER(Purchaser.class),
    ADMIN(Admin.class),
    USER(User.class)

    ;

    private Class<? extends Role> roleClass;

    RoleType(Class<? extends Role> roleClass) {
        this.roleClass = roleClass;
    }

    public Class<? extends Role> getRoleClass() {
        return roleClass;
    }


}
