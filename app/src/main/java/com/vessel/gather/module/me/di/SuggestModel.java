package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.utils.HttpResultVoidFunc;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author vesselzhang
 * @date 2017/12/5
 */

@ActivityScope
public class SuggestModel extends BaseModel implements SuggestContract.Model {

    @Inject
    public SuggestModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Boolean> submitAdvice(String content) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .submitAdvice(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultVoidFunc());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}