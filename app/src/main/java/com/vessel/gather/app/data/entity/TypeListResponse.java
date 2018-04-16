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
         */

        private int typeId;
        private String typeName;

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
    }
}
