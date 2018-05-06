package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.data.entity.CollectResponse;
import com.vessel.gather.app.utils.HttpResultFunc;
import com.vessel.gather.app.utils.HttpResultVoidFunc;

import java.util.List;
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
public class CollectModel extends BaseModel implements CollectContract.Model {

    @Inject
    public CollectModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<CollectResponse.CollectsBean>> collectList(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .collectList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>())
                .map(collectResponse -> collectResponse.getCollects());
    }

    @Override
    public Observable<Boolean> collectOrCancel(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .collectOrCancel(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultVoidFunc());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}