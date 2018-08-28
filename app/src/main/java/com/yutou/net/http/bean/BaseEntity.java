package com.yutou.net.http.bean;

/**
 * @Description:解析实体基类
 * @Author: zhoujianyu
 * @Time: 2018/5/16 17:04
 */

public class BaseEntity<T> {
    private static String SUCCESS_CODE = "40000";//成功的code
    private String code;
    private String hint;
    private T list;


    public boolean isSuccess() {
        return SUCCESS_CODE.equals(getCode());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return hint;
    }

    public void setMsg(String hint) {
        this.hint = hint;
    }

    public T getList() {
        return list;
    }

    public void setList(T data) {
        this.list = data;
    }
}
