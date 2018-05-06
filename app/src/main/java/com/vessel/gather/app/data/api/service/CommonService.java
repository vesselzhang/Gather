package com.vessel.gather.app.data.api.service;


import com.vessel.gather.app.data.api.Api;
import com.vessel.gather.app.data.entity.AddressResponse;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse;
import com.vessel.gather.app.data.entity.CartListResponse;
import com.vessel.gather.app.data.entity.CheckVersionResponse;
import com.vessel.gather.app.data.entity.CollectResponse;
import com.vessel.gather.app.data.entity.CommonResponse;
import com.vessel.gather.app.data.entity.IndexResponse;
import com.vessel.gather.app.data.entity.NotePadResponse;
import com.vessel.gather.app.data.entity.OrderDetailResponse;
import com.vessel.gather.app.data.entity.OrderListResponse;
import com.vessel.gather.app.data.entity.ProductListResponse;
import com.vessel.gather.app.data.entity.ServiceListResponse;
import com.vessel.gather.app.data.entity.ShopInfoResponse;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.app.data.entity.UserInfoResponse;
import com.vessel.gather.app.data.entity.VariableResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public interface CommonService {

    //基础接口
    @FormUrlEncoded
    @POST(Api.sendSms)
    Observable<CommonResponse<Void>> sendSms(@Field("phone") String phone, @Field("type") int type);

    @FormUrlEncoded
    @POST(Api.registByPhone)
    Observable<CommonResponse<Void>> registByPhone(@FieldMap Map<String, Object> map); //phone, code, password

    @FormUrlEncoded
    @POST(Api.loginByPhone)
    Observable<CommonResponse<VariableResponse>> loginByPhone(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST(Api.resetPassword)
    Observable<CommonResponse<Void>> resetPassword(@FieldMap Map<String, Object> map); //phone, code, newPassword

    @Multipart
    @POST(Api.uploadFile)
    Observable<CommonResponse<VariableResponse>> uploadFile(@Part("file\"; filename=\"file_name.jpg\"") RequestBody body); //phone, code, newPassword

    @FormUrlEncoded
    @POST(Api.checkVersion)
    Observable<CommonResponse<CheckVersionResponse>> checkVersion(@Field("type") String type);



    //个人模块
    @POST(Api.queryInfo)
    Observable<CommonResponse<UserInfoResponse>> queryInfo();

    @FormUrlEncoded
    @POST(Api.updateInfo)
    Observable<CommonResponse<Void>> updateInfo(@FieldMap Map<String, Object> map); //icon, nickname, sex, signature

    @FormUrlEncoded
    @POST(Api.updatePassword)
    Observable<CommonResponse<Void>> updatePassword(@FieldMap Map<String, Object> map); //oldPassword, newPassword

    @FormUrlEncoded
    @POST(Api.authorityApply)
    Observable<CommonResponse<Void>> authorityApply(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(Api.submitAdvice)
    Observable<CommonResponse<Void>> submitAdvice(@FieldMap Map<String, Object> map);


    //地址相关
    @POST(Api.addressList)
    Observable<CommonResponse<AddressResponse>> addressList();

    @FormUrlEncoded
    @POST(Api.saveAddress)
    Observable<CommonResponse<Void>> saveAddress(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(Api.resetDefaultAddress)
    Observable<CommonResponse<Void>> resetDefaultAddress(@Field("addressId") String addressId);

    @FormUrlEncoded
    @POST(Api.removeAddress)
    Observable<CommonResponse<Void>> removeAddress(@Field("addressId") String addressId);


    //记事本
    @POST(Api.queryNotepad)
    Observable<CommonResponse<NotePadResponse>> queryNotepad();

    @FormUrlEncoded
    @POST(Api.saveNotepad)
    Observable<CommonResponse<Void>> saveNotepad(@Field("title") String title, @Field("time") String time, @Field("content") String content);

    @FormUrlEncoded
    @POST(Api.deleteNotepad)
    Observable<CommonResponse<Void>> deleteNotepad(@Field("notepadId") String nodepadId);


    //收藏
    @FormUrlEncoded
    @POST(Api.collectOrCancel)
    Observable<CommonResponse<Void>> collectOrCancel(@FieldMap Map<String, Object> map); //artisanId, shopId, type

    @FormUrlEncoded
    @POST(Api.collectList)
    Observable<CommonResponse<CollectResponse>> collectList(@FieldMap Map<String, Object> map); //type, page, pageSize


    //主页相关
    @POST(Api.indexInfo)
    Observable<CommonResponse<IndexResponse>> getIndexInfo();

    @FormUrlEncoded
    @POST(Api.queryServiceList)
    Observable<CommonResponse<ServiceListResponse>> queryServiceList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(Api.queryArtisanInfo)
    Observable<CommonResponse<ArtisanInfoResponse>> queryArtisanInfo(@Field("artisanId") int artisanId);

    @FormUrlEncoded
    @POST(Api.queryShopInfo)
    Observable<CommonResponse<ShopInfoResponse>> queryShopInfo(@Field("shopId") int shopId);

    @FormUrlEncoded
    @POST(Api.queryTypeList)
    Observable<CommonResponse<TypeListResponse>> queryTypeList(@Field("parentType") int parentType);

    //管理
    //店铺
    @POST(Api.authorityShopInfo)
    Observable<CommonResponse<ShopInfoResponse>> queryShopInfo();

    @FormUrlEncoded
    @POST(Api.updateShop)
    Observable<CommonResponse<Void>> updateShop(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(Api.queryProductList)
    Observable<CommonResponse<ProductListResponse>> queryProductList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(Api.saveProduct)
    Observable<CommonResponse<Void>> saveProduct(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(Api.removeProduct)
    Observable<CommonResponse<Void>> removeProduct(@Field("productId") int productId);

    //技工
    @POST(Api.authorityArtisanInfo)
    Observable<CommonResponse<ArtisanInfoResponse>> authorityArtisanInfo();

    @FormUrlEncoded
    @POST(Api.updateArtisan)
    Observable<CommonResponse<Void>> updateArtisan(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(Api.saveSkill)
    Observable<CommonResponse<Void>> saveSkill(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(Api.removeSkill)
    Observable<CommonResponse<Void>> removeSkill(@Field("skillId") int skillId);


    //商品模块
    @FormUrlEncoded
    @POST(Api.joinCart)
    Observable<CommonResponse<Void>> joinCart(@Field("type") int type, @Field("id") long id, @Field("num") int num);

    @POST(Api.cartList)
    Observable<CommonResponse<CartListResponse>> cartList();

    @FormUrlEncoded
    @POST(Api.editCartNum)
    Observable<CommonResponse<Void>> editCartNum(@Field("cartId") long type, @Field("num") int num);

    @FormUrlEncoded
    @POST(Api.delCartById)
    Observable<CommonResponse<Void>> delCartById(@Field("cartId") long type);

    @FormUrlEncoded
    @POST(Api.submitOrder)
    Observable<CommonResponse<Map<String, Integer>>> submitOrder(@Field("ids") String ids, @Field("addressId") int addressId);

    @FormUrlEncoded
    @POST(Api.payOrder)
    Observable<CommonResponse<Map<String, String>>> payOrder(@Field("orderId") String orderId);//TODO

    @FormUrlEncoded
    @POST(Api.orderDetail)
    Observable<CommonResponse<OrderDetailResponse>> orderDetail(@Field("orderId") String orderId);

    @FormUrlEncoded
    @POST(Api.orderList)
    Observable<CommonResponse<OrderListResponse>> orderList(@Field("orderId") String orderId);

    @FormUrlEncoded
    @POST(Api.cancelOrder)
    Observable<CommonResponse<Void>> cancelOrder(@Field("orderId") String orderId);

    @FormUrlEncoded
    @POST(Api.confirmOrder)
    Observable<CommonResponse<Void>> confirmOrder(@Field("orderId") String orderId);

    @FormUrlEncoded
    @POST(Api.evaluateOrder)
    Observable<CommonResponse<Void>> evaluateOrder(@Field("orderId") String orderId, @Field("evaluate") String evaluate);
}
