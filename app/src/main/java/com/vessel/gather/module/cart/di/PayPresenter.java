package com.vessel.gather.module.cart.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class PayPresenter extends BasePresenter<PayContract.Model, PayContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public PayPresenter(PayContract.Model model, PayContract.View rootView) {
        super(model, rootView);
    }

    public void payOrder(int orderId, int payMethod) {
        mModel.payOrder(orderId, payMethod)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Map<String, String>>(mErrorHandler) {
                    @Override
                    public void onNext(Map<String, String> map) {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}