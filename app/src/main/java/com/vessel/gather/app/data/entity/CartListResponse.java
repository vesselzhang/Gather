package com.vessel.gather.app.data.entity;

import java.io.Serializable;
import java.util.List;

public class CartListResponse implements Serializable {

    private List<CartsBean> carts;

    public List<CartsBean> getCarts() {
        return carts;
    }

    public void setCarts(List<CartsBean> carts) {
        this.carts = carts;
    }

    public static class CartsBean implements Serializable {
        /**
         * cartId : 543534
         * type : 1
         * num : 3
         * price : 100
         * artisanId : 543534
         * realName : 技工名
         * skillId : 43242
         * skillName : 修水管
         * skillPic : /files/11.jpg
         * shopId : 321312
         * shopName : 店铺名
         * productId : 324
         * productName : 商品名
         * productPic : 商品图片地址
         */

        private long cartId;
        private int type;
        private int num;
        private float price;
        private long artisanId;
        private String realName;
        private long skillId;
        private String skillName;
        private String skillPic;
        private long shopId;
        private String shopName;
        private long productId;
        private String productName;
        private String productPic;
        private boolean isTitle;
        private List<CartsBean> cartDetail;

        public long getCartId() {
            return cartId;
        }

        public void setCartId(long cartId) {
            this.cartId = cartId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public long getArtisanId() {
            return artisanId;
        }

        public void setArtisanId(long artisanId) {
            this.artisanId = artisanId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

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

        public long getShopId() {
            return shopId;
        }

        public void setShopId(long shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

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

        public boolean isTitle() {
            return isTitle;
        }

        public void setTitle(boolean title) {
            isTitle = title;
        }

        public List<CartsBean> getCartDetail() {
            return cartDetail;
        }

        public void setCartDetail(List<CartsBean> cartDetail) {
            this.cartDetail = cartDetail;
        }
    }
}
