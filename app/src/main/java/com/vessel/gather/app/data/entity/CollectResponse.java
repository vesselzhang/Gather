package com.vessel.gather.app.data.entity;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author vesselzhang
 * @date 2017/12/11
 */

public class CollectResponse implements Serializable {

    private List<CollectsBean> collects;

    public List<CollectsBean> getCollects() {
        return collects;
    }

    public void setCollects(List<CollectsBean> collects) {
        this.collects = collects;
    }

    public static class CollectsBean implements MultiItemEntity {
        public static final int TYPE_COLLECT_ARTISAN = 0;
        public static final int TYPE_COLLECT_SHOP = 1;
        /**
         * type : 0
         * artisan : {"artisanId":1,"userId":1,"realPhoto":"files/2017.../asdasd.jpg","realName":"王彪","score":5,"skills":["门窗","瓷砖"]}
         * shop : {"shopId":8,"userId":11,"shopName":"旺旺杂货铺","shopPic":"/files/2017.../ajksdhjk.jpg","score":5}
         */

        private int type;
        private boolean select;
        private ArtisanBean artisan;
        private ShopBean shop;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public ArtisanBean getArtisan() {
            return artisan;
        }

        public void setArtisan(ArtisanBean artisan) {
            this.artisan = artisan;
        }

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        @Override
        public int getItemType() {
            return type;
        }

        public static class ArtisanBean {
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

        public static class ShopBean {
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
}
