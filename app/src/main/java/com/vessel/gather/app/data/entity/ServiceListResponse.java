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

        private long artisanId;
        private long userId;
        private String realPhoto;
        private String realName;
        private double score;
        private Integer workYear;
        private String type;
        private int saleNum;
        private List<String> skills;

        public long getArtisanId() {
            return artisanId;
        }

        public void setArtisanId(long artisanId) {
            this.artisanId = artisanId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
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

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public Integer getWorkYear() {
            return workYear;
        }

        public void setWorkYear(Integer workYear) {
            this.workYear = workYear;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(int saleNum) {
            this.saleNum = saleNum;
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

        private long shopId;
        private long userId;
        private String shopName;
        private String shopPic;
        private double score;
        private int saleNum;

        public long getShopId() {
            return shopId;
        }

        public void setShopId(long shopId) {
            this.shopId = shopId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
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

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(int saleNum) {
            this.saleNum = saleNum;
        }
    }
}
