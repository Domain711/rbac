package com.yunos.rbac.controller.auth;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yunos.rbac.base.contants.GlobalContents;
import com.yunos.rbac.base.dto.BaseDto;
import com.yunos.rbac.base.dto.PermissionDto;
import com.yunos.rbac.entity.menu.MenuEntity;
import com.yunos.rbac.entity.role.RoleEntity;
import com.yunos.rbac.exception.ErrorCode;
import com.yunos.rbac.service.auth.AuthService;
import com.yunos.rbac.service.menu.MenuService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 权限controller
 */

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;


    /**
     * 查询需要授权的角色
     *
     * @param model
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/auth/queryRoleList")
    String queryRoleList(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request) {
        List<RoleEntity> list = authService.queryAllRole(page);
        String pager = PageTool.getInstance().setData(list, request).render();
        model.addAttribute("pageInfo", list);
        model.addAttribute("page", pager);
        return "auth/authRoleList";
    }

    /**
     * 角色授权
     *
     * @param model
     * @param roleId
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/partitionAuth")
    String partitionAuth(Model model, @Param("roleId") Integer roleId, @Param("id") Integer id) {
        List<MenuEntity> menuList = menuService.queryAllMenu();
        model.addAttribute("data", GsonUtil.gson2String(menuList));
        RoleEntity role = roleService.getRoleById(roleId);
        model.addAttribute("role", role);
        long[] perms = authService.queryExistsPerm(roleId);
        model.addAttribute("existsPer", GsonUtil.gson2String(perms));
        return "auth/partitionAuth";
    }

    /**
     * 保存新赠菜单信息
     *
     * @param menu
     * @return
     */
    @PostMapping("/saveRolePermAuth")
    @ResponseBody
    String saveRolePermAuth(@Param("roleId") Long roleId, @Param("permiss") String permission) {
        BaseDto bd = new BaseDto();
        try {
            PermissionDto permissionDto = GsonUtil.gson2Bean(permission, PermissionDto.class);
            if (null == permissionDto.getData() || 0 == permissionDto.getData().size()) {//权限变为空
                //删除原有角色权限
                authService.delRolePermAuth(roleId);
                bd.setSucceed(GlobalContents.OPRATION_SUCESS);
            } else {//
                //删除原有角色权限
                authService.delRolePermAuth(roleId);
                int res = authService.saveRolePermAuth(roleId, permission);
                bd.setSucceed(res > 0 ? GlobalContents.OPRATION_SUCESS : GlobalContents.OPRATION_FAILD);
            }
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
