package com.yunos.rbac.dao.role;

import com.yunos.rbac.entity.role.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色Dao
 */
@Repository
public interface RoleDao {
    /**
     * 查询所有菜单
     *
     * @return
     */
    List<RoleEntity> queryAllRole();

    /**
     * 根据角色id获取角色信息
     * @param roleId
     * @return
     */
    RoleEntity getRoleById(Integer roleId);

    /**
     * 保存角色信息
     *
     * @param role
     * @return
     */
    int saveRole(RoleEntity role);

    /**
     * 更新角色信息
     *
     * @param role
     * @return
     */
    int updateRole(RoleEntity role);

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    int deleteRole(String roleId);
}
