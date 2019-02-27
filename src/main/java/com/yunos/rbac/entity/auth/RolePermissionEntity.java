package com.yunos.rbac.entity.auth;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 角色权限
 */
public class RolePermissionEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 角色id
     */
    @Valid
    @NotBlank(message = "角色id不能为空")
    private Long roleId;

    /**
     * 菜单Id
     */
    @Valid
    @NotBlank(message = "菜单Id不能为空")
    private Long menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
