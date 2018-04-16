package com.vessel.gather.app.data.entity;

import com.vessel.gather.BuildConfig;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jimq
 * @date 2017/12/6
 */

public class IndexResponse implements Serializable {

    private List<BannersBean> banners;

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public static class BannersBean {
        /**
         * bannerId : 1
         * bannerPic : /files/2017.../asdasd.jpg
         * linkType : 0
         * link : http://www.baidu.com
         * shopId : 22
         * productId : 11
         */

        private int bannerId;
        private String bannerPic;
        private int linkType;
        private String link;
        private int shopId;
        private int productId;

        public int getBannerId() {
            return bannerId;
        }

        public void setBannerId(int bannerId) {
            this.bannerId = bannerId;
        }

        public String getBannerPic() {
            return BuildConfig.APP_DOMAIN + bannerPic;
        }

        public void setBannerPic(String bannerPic) {
            this.bannerPic = bannerPic;
        }

        public int getLinkType() {
            return linkType;
        }

        public void setLinkType(int linkType) {
            this.linkType = linkType;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }
    }
}
