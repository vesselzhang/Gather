package com.vessel.gather.app.data.entity;

import java.io.Serializable;

/**
 * @author vesselzhang
 * @date 2017/12/2
 */

public class CheckVersionResponse implements Serializable {

    private String content;
    private int versionCode;
    private String downloadUrl;
    private String versionName;
    private int must;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getMust() {
        return must;
    }

    public void setMust(int must) {
        this.must = must;
    }
}
