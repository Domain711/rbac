package com.yunos.rbac.controller.role;

import com.yunos.rbac.base.contants.GlobalContents;
import com.yunos.rbac.base.dto.BaseDto;
import com.yunos.rbac.entity.menu.MenuEntity;
import com.yunos.rbac.entity.role.RoleEntity;
import com.yunos.rbac.exception.ErrorCode;
import com.yunos.rbac.service.role.RoleService;
import com.yunos.rbac.util.GsonUtil;
import com.yunos.rbac.util.PageTool;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 角色管理controller
 */

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询所有菜单
     *
     * @param model
     * @return
     */
    @GetMapping("/queryAllRole")
    String queryAllRole(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request) {
        List<RoleEntity> list = roleService.queryAllRole(page);
        String pager = PageTool.getInstance().setData(list, request).render();
        model.addAttribute("pageInfo", list);
        model.addAttribute("page", pager);
        return "role/roleList";
    }

    /**
     * 新增角色
     *
     * @param model
     * @return
     */
    @GetMapping("/addRole")
    String addRole(Model model, @Param("roleId") Integer roleId) {
        if (null != roleId) {
            RoleEntity role = roleService.getRoleById(roleId);
            model.addAttribute("role", role);
        }
        return "role/addRole";
    }


    /**
     * 保存新赠角色信息
     *
     * @param role
     * @return
     */
    @PostMapping("/saveRole")
    @ResponseBody
    String saveRole(@Validated RoleEntity role, BindingResult errors) {
        BaseDto bd = new BaseDto();
        StringBuilder validErrors = new StringBuilder();
        try {
            if (errors.hasErrors()) {
                bd.setSucceed(GlobalContents.OPRATION_FAILD);
                bd.setCode(ErrorCode.ERROR.getCode());
                if (errors != null) {
                    List<ObjectError> allErrors = errors.getAllErrors();
                    for (ObjectError error : allErrors) {
                        String message = error.getDefaultMessage();
                        validErrors.append(message).append(",");
                    }
                }
                bd.setMsg(validErrors.toString());
            } else {
                int res = null != role.getId() ? roleService.updateRole(role) : roleService.saveRole(role);
                bd.setSucceed(res > 0 ? GlobalContents.OPRATION_SUCESS : GlobalContents.OPRATION_FAILD);
                bd.setCode(ErrorCode.SUCCESS.getCode());
                bd.setMsg(ErrorCode.SUCCESS.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            bd.setSucceed(GlobalContents.OPRATION_FAILD);
            bd.setCode(ErrorCode.ERROR.getCode());
            bd.setMsg(ErrorCode.ERROR.getMsg());
        }
        return GsonUtil.gson2String(bd);
    }


    /**
     * 删除角色
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteRole")
    @ResponseBody
    String deleteRole(String roleId) {
        BaseDto bd = new BaseDto();
        try {
            int res = roleService.deleteRole(roleId);
            bd.setSucceed(res > 0 ? GlobalContents.OPRATION_SUCESS : GlobalContents.OPRATION_FAILD);
            bd.setCode(ErrorCode.SUCCESS.getCode());
            bd.setMsg(ErrorCode.SUCCESS.getMsg());
        } catch (Exception e) {
            bd.setSucceed(GlobalContents.OPRATION_FAILD);
            bd.setCode(ErrorCode.ERROR.getCode());
            bd.setMsg(ErrorCode.ERROR.getMsg());
        }
        return GsonUtil.gson2String(bd);
    }
}
