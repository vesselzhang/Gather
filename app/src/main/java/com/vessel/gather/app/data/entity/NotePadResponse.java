package com.vessel.gather.app.data.entity;

import java.io.Serializable;

/**
 * @author vesselzhang
 * @date 2017/12/5
 */

public class NotePadResponse implements Serializable {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
