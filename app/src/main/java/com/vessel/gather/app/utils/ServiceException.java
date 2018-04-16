package com.vessel.gather.app.utils;

/**
 * @author vesselzhang
 * @date 2017/12/2
 */

public class ServiceException extends RuntimeException {
    int code = -1;
    String msg = "";

    public ServiceException(int cause, String msg) {
        this.code = cause;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
