package com.yunos.rbac.dao.user;

import com.yunos.rbac.entity.auth.UserRoleEntity;
import com.yunos.rbac.entity.user.UserAdminEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台用户
 *
 * @date 2019-02-13 13:41:35
 */
@Repository
public interface UserAdminDao {


    /**
     * 新增
     *
     * @param user
     * @return
     */
    int addUser(UserAdminEntity user);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    int mergeUser(UserAdminEntity user);

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    int deleteUser(String userId);

    /**
     * 修改
     *
     * @param user
     * @return
     */
    int editUser(UserAdminEntity user);


    /**
     * 查询所有数据
     *
     * @return
     */
    List<UserAdminEntity> queryAll();

    /**
     * 获取用户已拥有的角色
     * @return
     */
    List<UserRoleEntity> queryUserRole(Long userId);

    /**
     * 根据用户ID获取用户对象
     *
     * @param userId
     * @return
     */
    UserAdminEntity getUserById(String userId);

    /**
     * 保存用户分配的角色信息
     * @param ure
     * @return
     */
    int savePartionRole(UserRoleEntity ure);


    /**
     * 删除用户分配的角色信息
     * @param userId
     * @return
     */
    int deleteUserRole(Long userId);


}
