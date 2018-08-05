package com.vessel.gather.module.cart.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.utils.HttpResultFunc;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class PayModel extends BaseModel implements PayContract.Model {

    @Inject
    public PayModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Map<String, String>> payOrder(int orderId, int payMethod) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .payOrder(orderId, payMethod)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}