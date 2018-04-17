package com.vessel.gather.module.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.app.data.entity.IndexResponse;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void getIndexInfo() {
        mModel.getIndexInfo()
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<IndexResponse>(mErrorHandler) {
                    @Override
                    public void onNext(IndexResponse indexResponse) {
                        EventBus.getDefault().post(indexResponse, Constants.TAG_BANNERS_REFRESH);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

}