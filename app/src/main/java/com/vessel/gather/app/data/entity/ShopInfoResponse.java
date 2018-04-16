package com.vessel.gather.app.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jimq
 * @date 2017/12/6
 */

public class ShopInfoResponse implements Serializable {

    /**
     * shopId : 8
     * userId : 11
     * shopName : 旺旺杂货铺
     * shopPic : /files/2017.../ajksdhjk.jpg
     * address : 四川省成都市金牛区火车北站xx号5-12
     * score : 5
     * phone : 18300000000
     * isCollect : 0
     * products : [{"productId":1,"productName":"矿泉水","productPic":"/files/.../asdsd.jpg","price":200,"remark":"备注"}]
     */

    private int shopId;
    private int userId;
    private String shopName;
    private String shopPic;
    private String address;
    private int score;
    private String phone;
    private int isCollect;
    private int typeId;
    private String city;
    private List<ProductsBean> products;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean implements Serializable{
        /**
         * productId : 1
         * productName : 矿泉水
         * productPic : /files/.../asdsd.jpg
         * price : 200
         * remark : 备注
         */

        private int productId;
        private String productName;
        private String productPic;
        private int price;
        private String remark;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
