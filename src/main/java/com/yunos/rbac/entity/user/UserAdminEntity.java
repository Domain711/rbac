package com.yunos.rbac.entity.user;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户
 *
 * @date 2019-02-13 13:41:35
 */
public class UserAdminEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空")
    private String nickname;
    /**
     * 用户名
     */
    @NotNull(message = "姓名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     * 手机号码
     */
    @Valid
    @NotNull(message = "手机号码不能为空")
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号格式不正确")
    private String phone;
    /**
     * 邮箱
     */
    @NotNull(message = "邮箱不能为空")
    private String email;
    /**
     * 头像
     */
    @NotNull(message = "头像不能为空")
    private String headPhoto;
    /**
     * 性别：1男，2女
     */
    @NotNull(message = "性别不能为空")
    private Integer sex;
    /**
     * 是否锁定用户：1是，0否
     */
    private Integer isLock;
    /**
     * 过期时间
     */
    private Date expTime;
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
     * 设置：昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取：昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置：用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取：用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置：密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取：手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置：邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置：头像
     */
    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    /**
     * 获取：头像
     */
    public String getHeadPhoto() {
        return headPhoto;
    }

    /**
     * 设置：性别：1男，2女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取：性别：1男，2女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置：是否锁定用户：1是，0否
     */
    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    /**
     * 获取：是否锁定用户：1是，0否
     */
    public Integer getIsLock() {
        return isLock;
    }

    /**
     * 设置：过期时间
     */
    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    /**
     * 获取：过期时间
     */
    public Date getExpTime() {
        return expTime;
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
