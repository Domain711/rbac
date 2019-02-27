package com.yunos.rbac.service.serviceImpl.auth;

import com.github.pagehelper.PageHelper;
import com.google.gson.internal.LinkedTreeMap;
import com.yunos.rbac.base.contants.GlobalContents;
import com.yunos.rbac.base.dto.PermissionDto;
import com.yunos.rbac.dao.auth.AuthDao;
import com.yunos.rbac.dao.role.RoleDao;
import com.yunos.rbac.entity.auth.RolePermissionEntity;
import com.yunos.rbac.entity.menu.MenuEntity;
import com.yunos.rbac.entity.role.RoleEntity;
import com.yunos.rbac.service.auth.AuthService;
import com.yunos.rbac.service.role.RoleService;
import com.yunos.rbac.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色service实现
 */

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthDao authDao;

    @Override
    public List<RoleEntity> queryAllRole(Integer page) {
        PageHelper.startPage(page, GlobalContents.PAGE_SIZE);
        List<RoleEntity> list = authDao.queryAllRole();
        return list;
    }


    @Override
    @Transactional
    public int saveRolePermAuth(Long roleId, String permission) {
        int count = 0;
        try {
            PermissionDto permissionDto = GsonUtil.gson2Bean(permission, PermissionDto.class);
            for (MenuEntity menu : permissionDto.getData()) {
                RolePermissionEntity rpe = new RolePermissionEntity();
                rpe.setRoleId(roleId);
                rpe.setMenuId(menu.getId());
                count = authDao.saveRolePermAuth(rpe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }


}
