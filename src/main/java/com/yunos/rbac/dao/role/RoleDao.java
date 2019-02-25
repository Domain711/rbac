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
     * 保存菜单信息
     *
     * @param menu
     * @return
     */
    int saveRole(RoleEntity menu);

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    int deleteRole(String roleId);
}
