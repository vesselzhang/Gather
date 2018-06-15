package com.vessel.gather.app.data.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vessel.gather.app.constant.SearchType;

import java.io.Serializable;
import java.util.List;

public class SearchResponse implements Serializable {

    private List<ProductsBean> products;
    private List<ArtisansBean> artisans;
    private List<ShopsBean> shops;
    private List<SkillsBean> skills;

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

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

    public List<SkillsBean> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillsBean> skills) {
        this.skills = skills;
    }

    public static class ProductsBean implements Serializable, MultiItemEntity {
        /**
         * productId : 1
         * productName : 矿泉水
         * productPic : /files/.../asdsd.jpg
         * price : 200
         * remark : 备注
         */

        private long productId;
        private String productName;
        private String productPic;
        private float price;
        private String remark;

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public int getItemType() {
            return SearchType.TYPE_PRODUCT;
        }
    }

    public static class ArtisansBean implements Serializable, MultiItemEntity {
        /**
         * artisanId : 1
         * userId : 1
         * realPhoto : files/2017.../asdasd.jpg
         * realName : 王彪
         * score : 5
         * saleNum : 5
         * skills : ["门窗","瓷砖"]
         */

        private long artisanId;
        private long userId;
        private String realPhoto;
        private String realName;
        private double score;
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

        @Override
        public int getItemType() {
            return SearchType.TYPE_ARTISAN;
        }
    }

    public static class ShopsBean implements Serializable, MultiItemEntity {
        /**
         * shopId : 8
         * userId : 11
         * shopName : 旺旺杂货铺
         * shopPic : /files/2017.../ajksdhjk.jpg
         * score : 5
         * saleNum : 5
         */

        private long shopId;
        private long userId;
        private String shopName;
        private String shopPic;
        private int score;
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

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(int saleNum) {
            this.saleNum = saleNum;
        }

        @Override
        public int getItemType() {
            return SearchType.TYPE_SHOP;
        }
    }

    public static class SkillsBean implements Serializable, MultiItemEntity {
        /**
         * skillId : 12
         * skillName : 专业打胶美缝
         * skillPic : /files/2017.../asdd.jpg
         * price : 5000
         * unit : 单位
         * remark : 备注
         */

        private long skillId;
        private String skillName;
        private String skillPic;
        private float price;
        private String unit;
        private String remark;

        public long getSkillId() {
            return skillId;
        }

        public void setSkillId(long skillId) {
            this.skillId = skillId;
        }

        public String getSkillName() {
            return skillName;
        }

        public void setSkillName(String skillName) {
            this.skillName = skillName;
        }

        public String getSkillPic() {
            return skillPic;
        }

        public void setSkillPic(String skillPic) {
            this.skillPic = skillPic;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public int getItemType() {
            return SearchType.TYPE_SKILL;
        }
    }
}
