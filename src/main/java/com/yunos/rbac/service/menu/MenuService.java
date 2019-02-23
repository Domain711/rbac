package com.yunos.rbac.service.menu;

import com.yunos.rbac.entity.menu.MenuEntity;

import java.util.List;

/**
 * 菜单 service
 */
public interface MenuService {

    /**
     * 查询所有菜单
     * @return
     */
    List<MenuEntity> queryAllMenu();

    /**
     * 根据ID获取菜单对象
     * @param id
     * @return
     */
    MenuEntity queryMenuById(Integer id);

    /**
     * 查询上级菜单
     * @return
     */
    List<MenuEntity> queryParentMenu();

    /**
     * 保存新增菜单
     * @return
     */
    int saveMenu(MenuEntity menu);

    /**
     * 修改保存
     * @param menu
     * @return
     */
    int saveEditMenu(MenuEntity menu);
}
