package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class SearchModel extends BaseModel implements SearchContract.Model {

    @Inject
    public SearchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

//    @Override
//    public Observable<CheckVersionResponse> checkVersion() {
//        return mRepositoryManager.obtainRetrofitService(CommonService.class)
//                .checkVersion("Android")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new HttpResultFunc<>());
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}