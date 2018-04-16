package com.vessel.gather.app.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jimq
 * @date 2017/12/6
 */

public class ArtisanInfoResponse implements Serializable {

    /**
     * artisanId : 1
     * userId : 1
     * realPhoto : files/2017.../asdasd.jpg
     * realName : 王彪
     * score : 5
     * address : 四川省成都市金牛区火车北站xx号5-12
     * phone : 18300000000
     * isCollect : 0
     * skills : [{"skillName":"专业打胶美缝","skillPic":"/files/2017.../asdd.jpg","price":5000,"remark":"备注"}]
     */

    private int artisanId;
    private int userId;
    private String realPhoto;
    private String realName;
    private int score;
    private String address;
    private String phone;
    private int isCollect;
    private int typeId;
    private String city;
    private List<SkillsBean> skills;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<SkillsBean> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillsBean> skills) {
        this.skills = skills;
    }

    public static class SkillsBean implements Serializable {
        /**
         * skillId : 12
         * skillName : 专业打胶美缝
         * skillPic : /files/2017.../asdd.jpg
         * price : 5000
         * remark : 备注
         */

        private int skillId;
        private String skillName;
        private String skillPic;
        private int price;
        private String remark;

        public int getSkillId() {
            return skillId;
        }

        public void setSkillId(int skillId) {
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
