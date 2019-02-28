package com.yunos.rbac.service.serviceImpl.menu;

import com.yunos.rbac.dao.menu.MenuDao;
import com.yunos.rbac.entity.menu.MenuEntity;
import com.yunos.rbac.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单实现类
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuEntity> queryAllMenu() {
        List<MenuEntity> list = menuDao.queryAllMenu();
        return list;
    }

    @Override
    public List<MenuEntity> queryAuthMenu(Long userId) {
        List<MenuEntity> list = menuDao.queryAuthMenu(userId);
        return list;
    }

    @Override
    public MenuEntity queryMenuById(Integer id) {
        MenuEntity menu = menuDao.queryMenuById(id);
        return menu;
    }

    @Override
    public List<MenuEntity> queryParentMenu() {
        List<MenuEntity> list = menuDao.queryParentMenu();
        return list;
    }

    @Override
    @Transactional
    public int saveMenu(MenuEntity menu) {
        int count = menuDao.saveMenu(menu);
        return count;
    }

    @Override
    @Transactional
    public int saveEditMenu(MenuEntity menu) {
        int count = menuDao.saveEditMenu(menu);
        return count;
    }
}
