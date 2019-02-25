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
}
