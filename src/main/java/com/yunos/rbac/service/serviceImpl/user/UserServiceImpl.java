package com.yunos.rbac.service.serviceImpl.user;

import com.github.pagehelper.PageHelper;
import com.yunos.rbac.base.contants.GlobalContents;
import com.yunos.rbac.dao.user.UserAdminDao;
import com.yunos.rbac.entity.user.UserAdminEntity;
import com.yunos.rbac.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public int deleteUser(String userId) {
        int count = userDao.deleteUser(userId);
        return count;
    }
}
