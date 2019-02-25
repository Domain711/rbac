package com.yunos.rbac.service.user;


import com.yunos.rbac.entity.user.UserAdminEntity;

import java.util.List;

/**
 * 用户service
 */
public interface UserService {

    /**
     * 查询所有用户
     * @return
     * @page
     */
    List<UserAdminEntity> queryALl(Integer page);

    /**
     * 根据用户ID获取用户对象
     * @param userId
     * @return
     */
    UserAdminEntity getUserById(String userId);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int addUser(UserAdminEntity user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int mergeUser(UserAdminEntity user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int deleteUser(String userId);

}
