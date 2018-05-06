package com.vessel.gather.module.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.data.entity.CheckVersionResponse;
import com.vessel.gather.app.data.entity.IndexResponse;
import com.vessel.gather.app.utils.HttpResultFunc;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<IndexResponse> getIndexInfo() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getIndexInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>());
    }

    @Override
    public Observable<CheckVersionResponse> checkVersion() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .checkVersion("Android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}