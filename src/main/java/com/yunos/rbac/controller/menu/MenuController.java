package com.yunos.rbac.controller.menu;

import com.yunos.rbac.base.contants.GlobalContents;
import com.yunos.rbac.base.dto.BaseDto;
import com.yunos.rbac.base.dto.TreeBaseDto;
import com.yunos.rbac.entity.menu.MenuEntity;
import com.yunos.rbac.exception.ErrorCode;
import com.yunos.rbac.service.menu.MenuService;
import com.yunos.rbac.util.GsonUtil;
import com.yunos.rbac.util.MenuUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单controller
 */

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询所有菜单
     *
     * @param model
     * @return
     */
    @GetMapping("/queryAllMenu")
    String queryAllMenu(Model model) {
        List<MenuEntity> menuList = menuService.queryAllMenu();
        model.addAttribute("data", GsonUtil.gson2String(menuList));
        return "menu/menuList";
    }

    /**
     * 选择父级菜单
     *
     * @param model
     * @return
     */
    @GetMapping("/getParentMenu")
    @ResponseBody
    String getParentMenu(Model model) {
        List<MenuEntity> menuList = menuService.queryParentMenu();
        List<TreeBaseDto> data = new ArrayList<>();
        TreeBaseDto root = new TreeBaseDto();
        root.setId(GlobalContents.ROOT_ID);
        root.setName("根目录");
        data.add(root);

        //获取所有一级菜单
        List<MenuEntity> rootMenu = menuList.stream().filter(item -> item.getPid() == 0).collect(Collectors.toList());

        for (MenuEntity menu : rootMenu) {
            TreeBaseDto treeBaseData = new TreeBaseDto();
            treeBaseData.setId(menu.getId());
            treeBaseData.setName(menu.getName());
            List<TreeBaseDto> childMenu = MenuUtil.getChildMenu(menu, menuList);
            treeBaseData.setChildren(childMenu);
            data.add(treeBaseData);
        }
        return GsonUtil.gson2String(data);
    }


    /**
     * 新增菜单
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/addMenu")
    String addMenu(Model model, @Param("id") Integer id) {
        return "menu/addMenu";
    }


    @GetMapping("/editMenu")
    String editMenu(Model model, @Param("id") Integer id) {
        MenuEntity menu = menuService.queryMenuById(id);
        model.addAttribute("menu", menu);
        return "menu/editMenu";
    }

    /**
     * 保存新赠菜单信息
     *
     * @param menu
     * @return
     */
    @PostMapping("/saveMenu")
    @ResponseBody
    String saveMenu(@Validated MenuEntity menu, BindingResult errors) {
        BaseDto bd = new BaseDto();
        StringBuilder validErrors = new StringBuilder();
        try {
            int res = menuService.saveMenu(menu);
            bd.setSucceed(res > 0 ? GlobalContents.OPRATION_SUCESS : GlobalContents.OPRATION_FAILD);
            bd.setCode(ErrorCode.SUCCESS.getCode());
            bd.setMsg(ErrorCode.SUCCESS.getMsg());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            if (errors.hasErrors()) {
                if (errors != null) {
                    List<ObjectError> allErrors = errors.getAllErrors();
                    for (ObjectError error : allErrors) {
                        String message = error.getDefaultMessage();
                        validErrors.append(message).append(",");
                        System.out.printf(validErrors.toString());
                    }
                }
            }
            bd.setSucceed(GlobalContents.OPRATION_FAILD);
            bd.setCode(ErrorCode.ERROR.getCode());
            bd.setMsg(validErrors.toString());
        } catch (Exception e) {
            e.printStackTrace();
            bd.setSucceed(GlobalContents.OPRATION_FAILD);
            bd.setCode(ErrorCode.ERROR.getCode());
            bd.setMsg(ErrorCode.ERROR.getMsg());
        }
        return GsonUtil.gson2String(bd);
    }

    /**
     * 保存新赠菜单信息
     *
     * @param menu
     * @return
     */
    @PutMapping("/saveEditMenu")
    @ResponseBody
    String saveEditMenu(@Validated MenuEntity menu, BindingResult errors) {
        BaseDto bd = new BaseDto();
        StringBuilder validErrors = new StringBuilder();
        try {
            int res = menuService.saveEditMenu(menu);
            bd.setSucceed(res > 0 ? GlobalContents.OPRATION_SUCESS : GlobalContents.OPRATION_FAILD);
            bd.setCode(ErrorCode.SUCCESS.getCode());
            bd.setMsg(ErrorCode.SUCCESS.getMsg());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            if (errors.hasErrors()) {
                if (errors != null) {
                    List<ObjectError> allErrors = errors.getAllErrors();
                    for (ObjectError error : allErrors) {
                        String message = error.getDefaultMessage();
                        validErrors.append(message).append(",");
                        System.out.printf(validErrors.toString());
                    }
                }
            }
            bd.setSucceed(GlobalContents.OPRATION_FAILD);
            bd.setCode(ErrorCode.ERROR.getCode());
            bd.setMsg(validErrors.toString());
        } catch (Exception e) {
            e.printStackTrace();
            bd.setSucceed(GlobalContents.OPRATION_FAILD);
            bd.setCode(ErrorCode.ERROR.getCode());
            bd.setMsg(ErrorCode.ERROR.getMsg());
        }
        return GsonUtil.gson2String(bd);
    }

}
