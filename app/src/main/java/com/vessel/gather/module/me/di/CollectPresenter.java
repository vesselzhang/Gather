package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.vessel.gather.app.data.entity.CollectResponse;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class CollectPresenter extends BasePresenter<CollectContract.Model, CollectContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CollectPresenter(CollectContract.Model model, CollectContract.View rootView) {
        super(model, rootView);
    }

    public void collectList() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", 0);
        map.put("page", 1);
        map.put("pageSize", 100);
        mModel.collectList(map).subscribe(
                new ProgressSubscriber<List<CollectResponse.CollectsBean>>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(List<CollectResponse.CollectsBean> collectsBeans) {
                        super.onNext(collectsBeans);
                    }
                }
        );
    }

    public void collectOrCancel() {
        Map<String, Object> map = new HashMap<>();
        mModel.collectOrCancel(map).subscribe(
                new ProgressSubscriber<Boolean>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        super.onNext(aBoolean);
                    }
                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}