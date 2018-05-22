package com.vessel.gather.app.data.entity;

import java.io.Serializable;

/**
 * @author vesselzhang
 * @date 2017/12/11
 */

public class UserInfoResponse implements Serializable {

    /**
     * userId : 1
     * icon : /files/2017/10/10/kskjdijksasd.jpg
     * nickname : 灰太狼
     * sex : 0
     * signature : 我要吃羊！！
     * isMerchant : 0
     * isArtisan : 0
     * phone : 13312341234
     */

    private long userId;
    private String icon;
    private String nickname;
    private int sex;
    private String signature;
    private int isMerchant;
    private int isArtisan;
    private String phone;
    private long shopId;
    private long artisanId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getIsMerchant() {
        return isMerchant;
    }

    public void setIsMerchant(int isMerchant) {
        this.isMerchant = isMerchant;
    }

    public int getIsArtisan() {
        return isArtisan;
    }

    public void setIsArtisan(int isArtisan) {
        this.isArtisan = isArtisan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public long getArtisanId() {
        return artisanId;
    }

    public void setArtisanId(long artisanId) {
        this.artisanId = artisanId;
    }
}
