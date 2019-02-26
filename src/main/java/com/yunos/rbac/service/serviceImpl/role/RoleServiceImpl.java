package com.yunos.rbac.service.serviceImpl.role;

import com.github.pagehelper.PageHelper;
import com.yunos.rbac.base.contants.GlobalContents;
import com.yunos.rbac.dao.role.RoleDao;
import com.yunos.rbac.entity.role.RoleEntity;
import com.yunos.rbac.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色service实现
 */

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<RoleEntity> queryAllRole(Integer page) {
        PageHelper.startPage(page, GlobalContents.PAGE_SIZE);
        List<RoleEntity> list = roleDao.queryAllRole();
        return list;
    }

    @Override
    public RoleEntity getRoleById(Integer roleId) {
        return roleDao.getRoleById(roleId);
    }

    @Override
    @Transactional
    public int saveRole(RoleEntity role) {
        int count = roleDao.saveRole(role);
        return count;
    }

    @Override
    public int updateRole(RoleEntity role) {
        int count = roleDao.updateRole(role);
        return count;
    }

    @Override
    @Transactional
    public int deleteRole(String roleId) {
        int count = roleDao.deleteRole(roleId);
        return count;
    }
}
