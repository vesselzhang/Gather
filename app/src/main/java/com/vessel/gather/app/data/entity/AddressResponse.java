package com.vessel.gather.app.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author vesselzhang
 * @date 2017/12/2
 */

public class AddressResponse implements Serializable {
    private List<Address> addresses;

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public static class Address implements Serializable {
        /**
         * addressId : 1
         * name : 张三
         * phone : 18300000000
         * detailed : 四川省成都市武侯区创业大道203号...
         * isDefault : 0
         */

        private int addressId;
        private String name;
        private String phone;
        private String detailed;
        private int isDefault;

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

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

        public String getDetailed() {
            return detailed;
        }

        public void setDetailed(String detailed) {
            this.detailed = detailed;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }
    }
}
