package com.vessel.gather.app.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author vesselzhang
 * @date 2017/12/8
 */

public class ProductListResponse implements Serializable {

    private List<ProductsBean> products;

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
