package com.yunos.rbac.entity.auth;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 用户-角色
 */
public class UserRoleEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户id
     */
    @Valid
    @NotBlank(message = "用户id不能为空")
    private Long userId;

    /**
     * 角色Id
     */
    @Valid
    @NotBlank(message = "角色Id不能为空")
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
