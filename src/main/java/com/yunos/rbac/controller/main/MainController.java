package com.yunos.rbac.controller.main;

import com.yunos.rbac.base.dto.TreeBaseDto;
import com.yunos.rbac.entity.menu.MenuEntity;
import com.yunos.rbac.service.menu.MenuService;
import com.yunos.rbac.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
//        List<MenuEntity> menuList = menuService.queryParentMenu();
        List<MenuEntity> menuList = menuService.queryAuthMenu(7l);
        List<TreeBaseDto> data = null !=menuList ? MenuUtil.getMenuData(menuList) : null;
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
