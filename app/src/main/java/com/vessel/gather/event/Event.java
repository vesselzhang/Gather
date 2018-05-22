package com.vessel.gather.event;

/**
 * @author vesselzhang
 * @date 2017/12/13
 */

public class Event {
    public static final String EVENT_USERINFO = "event_user_info";
    public static final String EVENT_ADDRESS_UPDATE = "event_address_update";
    public static final String EVENT_CART_UPDATE = "event_cart_update";
    public static final String EVENT_DOWNLOAD_APK = "event_download_apk";

    private String downloadUrl;

    public Event() {

    }

    public Event(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
