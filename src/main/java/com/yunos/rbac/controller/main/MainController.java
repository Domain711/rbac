package com.yunos.rbac.controller.main;

import com.yunos.rbac.base.dto.TreeBaseDto;
import com.yunos.rbac.entity.menu.MenuEntity;
import com.yunos.rbac.service.menu.MenuService;
import com.yunos.rbac.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 主页面controller
 */

@Controller
public class MainController {

    @Autowired
    private MenuService menuService;

    /**
     * 主页
     *
     * @return
     */
    @GetMapping("/index")
    String index(Model model) {
        List<MenuEntity> menuList = menuService.queryParentMenu();

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

        model.addAttribute("data", data);
        return "/main/index";
    }


    /**
     * 主页的欢迎页
     *
     * @return
     */
    @GetMapping("/main/welcome")
    String welcome(Model model) {
        return "/main/welcome";
    }
}
