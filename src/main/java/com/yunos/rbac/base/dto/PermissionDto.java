package com.yunos.rbac.base.dto;

import com.yunos.rbac.entity.menu.MenuEntity;

import java.util.List;

/**
 * 授权数据传输
 */
public class PermissionDto {
    private Integer[] ids;
    private List<MenuEntity> data;


    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public List<MenuEntity> getData() {
        return data;
    }

    public void setData(List<MenuEntity> data) {
        this.data = data;
    }
}
