package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse;
import com.vessel.gather.app.utils.HttpResultFunc;
import com.vessel.gather.app.utils.HttpResultVoidFunc;

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
public class WorkerDetailModel extends BaseModel implements WorkerDetailContract.Model {

    @Inject
    public WorkerDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<ArtisanInfoResponse> queryArtisanInfo(long artisanId) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .queryArtisanInfo(artisanId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc());
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