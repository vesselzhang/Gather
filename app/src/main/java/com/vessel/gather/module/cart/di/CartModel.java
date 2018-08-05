package com.vessel.gather.module.cart.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.data.entity.CartListResponse.CartsBean;
import com.vessel.gather.app.utils.HttpResultFunc;
import com.vessel.gather.app.utils.HttpResultVoidFunc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class CartModel extends BaseModel implements CartContract.Model {

    @Inject
    public CartModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<CartsBean>> cartList() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .cartList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>())
                .map(cartListResponse -> {
                    List<CartsBean> newList = new ArrayList<>();
                    for (CartsBean each : cartListResponse.getCarts()) {
                        CartsBean titleTemp = null;
                        for (CartsBean title : newList) {
                            if (title.getShopId() == each.getShopId()) {
                                titleTemp = title;
                                break;
                            }
                        }
                        if (titleTemp == null) {
                            titleTemp = new CartsBean();
                            titleTemp.setTitle(true);
                            titleTemp.setShopName(each.getShopName());
                            newList.add(titleTemp);
                        }
                        titleTemp.getCartDetail().add(each);
                    }
                    return newList;
                });
    }

    @Override
    public Observable<Boolean> delCartById(String cartIds) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .delCartById(cartIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultVoidFunc());
    }

    @Override
    public Observable<Map<String, Integer>> submitOrder(String ids, int addressId) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .submitOrder(ids, addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}