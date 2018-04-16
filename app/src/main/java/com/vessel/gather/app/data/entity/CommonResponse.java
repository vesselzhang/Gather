package com.vessel.gather.app.data.entity;

/**
 * 通用的Http返回json解析类可以通过T来实现不用的结果
 *
 * @author vesselzhang
 * @date 2017/11/29
 */
public class CommonResponse<T> {

    /**
     * status : 0
     * msg : 登录成功
     * data : {}
     */

    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}