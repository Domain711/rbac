package com.yunos.rbac.base.dto;

/**
 * 响应信息封装
 */
public class BaseDto {

    //业务是否成功 true:成功 false:失败
    private boolean succeed;
    //响应代码 //业务成功== 0,失败== -1。其他值以后对应不同的操作
    private int code;
    //响应信息
    private String msg;
    //响应数据
    private Object data;

    public BaseDto() {
    }

    public BaseDto(boolean succeed, int code, String msg) {
        this.succeed = succeed;
        this.code = code;
        this.msg = msg;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
