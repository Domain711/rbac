package com.yunos.rbac.service.serviceImpl.user;

import com.github.pagehelper.PageHelper;
import com.yunos.rbac.base.contants.GlobalContents;
import com.yunos.rbac.dao.user.UserAdminDao;
import com.yunos.rbac.entity.auth.UserRoleEntity;
import com.yunos.rbac.entity.user.UserAdminEntity;
import com.yunos.rbac.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAdminDao userDao;

    @Override
    public List<UserAdminEntity> queryALl(Integer page) {
        PageHelper.startPage(page, GlobalContents.PAGE_SIZE);
        List<UserAdminEntity> res = userDao.queryAll();
        return res;
    }

    @Override
    @Transactional
    public int addUser(UserAdminEntity user) {
        int count = userDao.addUser(user);
        return count;
    }

    @Override
    public UserAdminEntity getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    @Override
    @Transactional
    public int mergeUser(UserAdminEntity user) {
        int count = userDao.mergeUser(user);
        return count;
    }

    @Override
    @Transactional
    public int deleteUser(String userId) {
        int count = userDao.deleteUser(userId);
        return count;
    }

    @Override
    @Transactional
    public void savePartitionRole(Long userId, String roleIds) {
        if (!StringUtils.isEmpty(roleIds)) {
            //清空原有分配的角色信息
            userDao.deleteUserRole(userId);
            String[] roles = roleIds.split(",");
            for (String role : roles) {
                UserRoleEntity ure = new UserRoleEntity();
                ure.setUserId(userId);
                ure.setRoleId(Long.valueOf(role));
                userDao.savePartionRole(ure);
            }
        } else {//清空原有的分配角色数据
            userDao.deleteUserRole(userId);
        }
    }

    @Override
    public List<UserRoleEntity> queryUserRole(Long userId) {
        List<UserRoleEntity> roleList = userDao.queryUserRole(userId);
        return roleList;
    }
}