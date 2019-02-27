package com.yunos.rbac.dao.auth;

import com.yunos.rbac.entity.auth.RolePermissionEntity;
import com.yunos.rbac.entity.role.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限Dao
 */
@Repository
public interface AuthDao {
    /**
     * 查询需要授权的角色
     *
     * @return
     */
    List<RoleEntity> queryAllRole();

    /**
     * 获取角色已有权限
     *
     * @param roleId
     * @return
     */
    List<RolePermissionEntity> queryExistsPerm(Integer roleId);

    /**
     * 保存角色权限信息
     *
     * @param rolePermission
     * @return
     */
    int saveRolePermAuth(RolePermissionEntity rolePermission);

    /**
     * 删除角色已有权限
     *
     * @param roleId
     */
    int delRolePermAuth(Long roleId);


}
