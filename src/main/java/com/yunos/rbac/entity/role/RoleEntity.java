package com.yunos.rbac.entity.role;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 角色表
 *
 * @date 2019-02-23 08:19:10
 */
public class RoleEntity implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 角色名称
     */
    @Valid
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
