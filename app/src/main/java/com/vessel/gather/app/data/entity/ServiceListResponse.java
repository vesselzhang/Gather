package com.vessel.gather.app.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jimq
 * @date 2017/12/6
 */

public class ServiceListResponse implements Serializable {


    private List<ArtisansBean> artisans;
    private List<ShopsBean> shops;

    public List<ArtisansBean> getArtisans() {
        return artisans;
    }

    public void setArtisans(List<ArtisansBean> artisans) {
        this.artisans = artisans;
    }

    public List<ShopsBean> getShops() {
        return shops;
    }

    public void setShops(List<ShopsBean> shops) {
        this.shops = shops;
    }

    public static class ArtisansBean {
        /**
         * artisanId : 1
         * userId : 1
         * realPhoto : files/2017.../asdasd.jpg
         * realName : 王彪
         * score : 5
         * skills : ["门窗","瓷砖"]
         */

        private int artisanId;
        private int userId;
        private String realPhoto;
        private String realName;
        private int score;
        private List<String> skills;

        public int getArtisanId() {
            return artisanId;
        }

        public void setArtisanId(int artisanId) {
            this.artisanId = artisanId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getRealPhoto() {
            return realPhoto;
        }

        public void setRealPhoto(String realPhoto) {
            this.realPhoto = realPhoto;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public List<String> getSkills() {
            return skills;
        }

        public void setSkills(List<String> skills) {
            this.skills = skills;
        }
    }

    public static class ShopsBean {
        /**
         * shopId : 8
         * userId : 11
         * shopName : 旺旺杂货铺
         * shopPic : /files/2017.../ajksdhjk.jpg
         * score : 5
         */

        private int shopId;
        private int userId;
        private String shopName;
        private String shopPic;
        private int score;

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopPic() {
            return shopPic;
        }

        public void setShopPic(String shopPic) {
            this.shopPic = shopPic;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
