package com.yunos.rbac.exception;

/**
 * 错误代码定义枚举
 */
public enum ErrorCode {
    SUCCESS(0, "成功"),
    ERROR(-1, "失败");


    private int code;
    private String msg;


    ErrorCode(int code) {
        this.code = code;
    }

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
