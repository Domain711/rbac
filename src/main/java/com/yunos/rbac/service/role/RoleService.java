package com.yunos.rbac.service.role;

import com.yunos.rbac.entity.menu.MenuEntity;
import com.yunos.rbac.entity.role.RoleEntity;

import java.util.List;

/**
 * 角色管理service
 */
public interface RoleService {
    /**
     * 查询所有角色
     * @return
     */
    List<RoleEntity> queryAllRole(Integer page);

    /**
     * 保存新增角色
     * @return
     */
    int saveRole(RoleEntity role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    int deleteRole(String roleId);
}
