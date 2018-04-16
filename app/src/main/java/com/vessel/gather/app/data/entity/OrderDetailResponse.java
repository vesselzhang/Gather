package com.vessel.gather.app.data.entity;

import java.util.List;

/**
 * @author vesselzhang
 * @date 2017/12/13
 */

public class OrderDetailResponse {

    /**
     * orderId : 31
     * address : {"name":"张三","phone":"18300000000","region":"四川省成都市青羊区","street":"阜南路","postcode":"622333","detailed":"创业大道203号..."}
     * orderDetail : [{"type":1,"num":3,"price":100,"artisanId":543534,"realName":"技工名","skillId":43242,"skillName":"修水管","skillPic":"/files/11.jpg","shopId":321312,"shopName":"店铺名","productId":324,"productName":"商品名","productPic":"商品图片地址"}]
     * orderPrice : 10000
     * orderNum : 5
     * logisticsName : 物流公司名称
     * logisticsNo : 1245646
     * orderStatus : 1
     */

    private long orderId;
    private AddressBean address;
    private float orderPrice;
    private int orderNum;
    private String logisticsName;
    private String logisticsNo;
    private int orderStatus;
    private List<OrderDetailBean> orderDetail;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderDetailBean> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailBean> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public static class AddressBean {
        /**
         * name : 张三
         * phone : 18300000000
         * region : 四川省成都市青羊区
         * street : 阜南路
         * postcode : 622333
         * detailed : 创业大道203号...
         */

        private String name;
        private String phone;
        private String region;
        private String street;
        private String postcode;
        private String detailed;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getDetailed() {
            return detailed;
        }

        public void setDetailed(String detailed) {
            this.detailed = detailed;
        }
    }

    public static class OrderDetailBean {
        /**
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
    }
}
