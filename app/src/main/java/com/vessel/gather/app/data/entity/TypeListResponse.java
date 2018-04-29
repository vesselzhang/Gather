package com.vessel.gather.app.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jmq90 on 2017/12/20 0020.
 */

public class TypeListResponse implements Serializable {

    private List<TypesBean> types;

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public static class TypesBean implements Serializable {
        /**
         * typeId : 1
         * typeName : 设计师
         * parentType : 0
         * createTime : 2018-01-13 15:22:17
         * updateTime : 2018-01-13 15:22:17
         * serial : 9
         */

        private int typeId;
        private String typeName;
        private int parentType;
        private String createTime;
        private String updateTime;
        private int serial;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getParentType() {
            return parentType;
        }

        public void setParentType(int parentType) {
            this.parentType = parentType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getSerial() {
            return serial;
        }

        public void setSerial(int serial) {
            this.serial = serial;
        }
    }
}
