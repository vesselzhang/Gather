package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.data.entity.ServiceListResponse;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.app.utils.HttpResultFunc;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class SellerListModel extends BaseModel implements SellerListContract.Model {

    @Inject
    public SellerListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<ServiceListResponse> queryServiceList(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .queryServiceList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc());
    }

    @Override
    public Observable<TypeListResponse> queryTypeList(int parentType) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .queryTypeList(parentType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}