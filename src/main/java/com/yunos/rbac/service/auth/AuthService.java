package com.yunos.rbac.service.auth;

import com.yunos.rbac.entity.role.RoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限管理service
 */
public interface AuthService {
    /**
     * 查询需要授权的角色
     *
     * @return
     */
    List<RoleEntity> queryAllRole(Integer page);


    /**
     * 保存权限信息
     *
     * @param permission
     * @return
     */
    int saveRolePermAuth(@Param("roleId") Long roleId, @Param("permission") String permission);

}
