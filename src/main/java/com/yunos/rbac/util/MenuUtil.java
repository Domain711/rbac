package com.yunos.rbac.util;

import com.yunos.rbac.base.dto.TreeBaseDto;
import com.yunos.rbac.entity.menu.MenuEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单工具类
 */
public class MenuUtil {

    /**
     * 递归查找菜单数据
     *
     * @param menu
     * @param menuList
     * @return
     */
    public static List<TreeBaseDto> getChildMenu(MenuEntity menu, List<MenuEntity> menuList) {
        List<MenuEntity> list = menuList.stream().filter(item -> item.getPid() == menu.getId()).collect(Collectors.toList());
        //查找下级节点
        List<TreeBaseDto> childList = new ArrayList<>();
        for (MenuEntity child : list) {
            TreeBaseDto childData = new TreeBaseDto();
            childData.setId(child.getId());
            childData.setName(child.getName());
            childData.setUrl(child.getUrl());
            childData.setIcon(child.getIcon());
            childData.setChildren(getChildMenu(child, menuList));
            childList.add(childData);
        }

        if (null == list || list.isEmpty())
            return null;
        return childList;
    }

    /**
     * 获取组装好的菜单数据
     *
     * @param menuList
     * @return
     */
    public static List<TreeBaseDto> getMenuData(List<MenuEntity> menuList) {
        List<TreeBaseDto> data = new ArrayList<>();
        //获取所有一级菜单
        List<MenuEntity> rootMenu = menuList.stream().filter(item -> item.getPid() == 0)
                .filter(item -> item.getType() == 1)
                .collect(Collectors.toList());
        for (MenuEntity menu : rootMenu) {
            TreeBaseDto treeBaseData = new TreeBaseDto();
            treeBaseData.setId(menu.getId());
            treeBaseData.setName(menu.getName());
            treeBaseData.setUrl(menu.getUrl());
            treeBaseData.setIcon(menu.getIcon());
            List<TreeBaseDto> childMenu = MenuUtil.getChildMenu(menu, menuList);
            treeBaseData.setChildren(childMenu);
            data.add(treeBaseData);
        }
        return data;

    }
}
