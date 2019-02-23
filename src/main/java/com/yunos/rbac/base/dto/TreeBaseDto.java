package com.yunos.rbac.base.dto;

import java.util.List;

/**
 * 树形下拉框数据结构
 */
public class TreeBaseDto {
    private Long id;
    private String name;
    private String icon;
    private String url;
    private Integer posation;
    private List<TreeBaseDto> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<TreeBaseDto> getChildren() {
        return children;
    }

    public void setChildren(List<TreeBaseDto> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPosation() {
        return posation;
    }

    public void setPosation(Integer posation) {
        this.posation = posation;
    }
}
