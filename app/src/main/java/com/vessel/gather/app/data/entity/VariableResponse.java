package com.vessel.gather.app.data.entity;

import java.io.Serializable;

/**
 * 集中收纳散落的message
 *
 * @author vesselzhang
 * @date 2017/12/2
 */

public class VariableResponse implements Serializable {

    /**
     * token : 1s1245qwe21q3we1
     * url : /files/2017/11/24/sssss.jpg
     */

    private String token;  //loginByPhone
    private String url;    //uploadFile

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
