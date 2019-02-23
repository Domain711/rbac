package com.yunos.rbac.entity.menu;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 后台菜单表
 *
 * @date 2019-02-23 08:19:10
 */
public class MenuEntity implements Serializable {

    /**
     * ID
     */
    private Long id;
    /**
     * 此表的父级ID
     */
    @NotNull(message = "父级菜单不能为空")
    private Long pid;
    /**
     * 菜单名称
     */
    @NotNull(message = "菜单名称不能为空")
    private String name;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 请求路径
     */
    @NotNull(message = "请求路径不能为空")
    private String url;
    /**
     * 授权路径规则
     */
    @NotNull(message = "授权路径规则不能为空")
    private String authUrl;
    /**
     * HTTP请求类型：1-GET,2-POST,3-PUT,4-DELETE,5-HEAD,6-OPTIONS,7-TRACE,8-PATCH
     */
    @NotNull(message = "请求方式不能为空")
    private Integer httpMethod;
    /**
     * 参数
     */
    private String param;
    /**
     * 菜单类型：1后台菜单，2前台菜单
     */
    @NotNull(message = "菜单分类不能为空")
    private Integer type;
    /**
     * 菜单类型;1:有界面可访问菜单,2:无界面可访问菜单,0:只作为菜单,3只作为分组标题
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;
    /**
     * 状态;1:显示,0:不显示
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Float listOrder;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 最后修改时间
     */
    private Date gmtModified;
    /**
     * 是否删除，否为null，是为时间
     */
    private Date isDel;

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：此表的父级ID
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 获取：此表的父级ID
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 设置：菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取：菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置：请求路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取：请求路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置：授权路径规则
     */
    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    /**
     * 获取：授权路径规则
     */
    public String getAuthUrl() {
        return authUrl;
    }

    /**
     * 设置：HTTP请求类型：1-GET,2-POST,3-PUT,4-DELETE,5-HEAD,6-OPTIONS,7-TRACE,8-PATCH
     */
    public void setHttpMethod(Integer httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**
     * 获取：HTTP请求类型：1-GET,2-POST,3-PUT,4-DELETE,5-HEAD,6-OPTIONS,7-TRACE,8-PATCH
     */
    public Integer getHttpMethod() {
        return httpMethod;
    }

    /**
     * 设置：参数
     */
    public void setParam(String param) {
        this.param = param;
    }

    /**
     * 获取：参数
     */
    public String getParam() {
        return param;
    }

    /**
     * 设置：菜单类型：1后台菜单，2前台菜单
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：菜单类型：1后台菜单，2前台菜单
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置：菜单类型;1:有界面可访问菜单,2:无界面可访问菜单,0:只作为菜单,3只作为分组标题
     */
    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取：菜单类型;1:有界面可访问菜单,2:无界面可访问菜单,0:只作为菜单,3只作为分组标题
     */
    public Integer getMenuType() {
        return menuType;
    }

    /**
     * 设置：状态;1:显示,0:不显示
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态;1:显示,0:不显示
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：排序
     */
    public void setListOrder(Float listOrder) {
        this.listOrder = listOrder;
    }

    /**
     * 获取：排序
     */
    public Float getListOrder() {
        return listOrder;
    }

    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置：创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取：创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置：最后修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取：最后修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置：是否删除，否为null，是为时间
     */
    public void setIsDel(Date isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取：是否删除，否为null，是为时间
     */
    public Date getIsDel() {
        return isDel;
    }
}
