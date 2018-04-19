package com.vessel.gather.app.data.api;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public interface Api {
    String base        = "assemble/app/v1.0";
    String basic       = base + "/basic/";
    String info        = base + "/info/";
    String index       = base + "/index/";
    String authority   = base + "/authority/";
    String product     = base + "/product/";

    //基础模块
    String sendSms                    = basic + "sendSms";
    String registByPhone              = basic + "registByPhone";
    String loginByPhone               = basic + "loginByPhone";
    String resetPassword              = basic + "resetPassword";
    String uploadFile                 = basic + "uploadFile";


    //个人模块
    String queryInfo                  = info + "queryInfo";
    String updateInfo                 = info + "updateInfo";
    String updatePassword             = info + "updatePassword";
    String addressList                = info + "addressList";
    String saveAddress                = info + "saveAddress";
    String resetDefaultAddress        = info + "resetDefaultAddress";
    String removeAddress              = info + "removeAddress";
    String queryNotepad               = info + "queryNotepad";
    String saveNotepad                = info + "saveNotepad";
    String deleteNotepad              = info + "deleteNotepad";
    String collectOrCancel            = info + "collectOrCancel";
    String collectList                = info + "collectList";
    String authorityApply             = info + "authorityApply";
    String submitAdvice               = info + "submitAdvice";


    //主页模块
    String indexInfo                   = index + "indexInfo";
    String queryServiceList            = index + "queryServiceList";
    String queryArtisanInfo            = index + "queryArtisanInfo";
    String queryShopInfo               = index + "queryShopInfo";
    String queryTypeList               = index + "queryTypeList";

    //权限模块
    String authorityShopInfo          = authority + "queryShopInfo";
    String updateShop                 = authority + "updateShop";
    String queryProductList           = authority + "queryProductList";
    String saveProduct                = authority + "saveProduct";
    String removeProduct              = authority + "removeProduct";
//    String removeProduct = authority + "removeProduct";
    String authorityArtisanInfo       = authority + "queryArtisanInfo";
    String updateArtisan              = authority + "updateArtisan";
    String saveSkill                  = authority + "saveSkill";
    String removeSkill                = authority + "removeSkill";

    //商品模块
    String joinCart                   = product + "joinCart";
    String cartList                   = product + "cartList";
    String editCartNum                = product + "editCartNum";
    String delCartById                = product + "delCartById";
    String submitOrder                = product + "submitOrder";
    String payOrder                   = product + "payOrder";
    String orderDetail                = product + "orderDetail";
    String orderList                  = product + "orderList";
    String cancelOrder                = product + "cancelOrder";
    String confirmOrder               = product + "confirmOrder";
    String evaluateOrder              = product + "evaluateOrder";
}
