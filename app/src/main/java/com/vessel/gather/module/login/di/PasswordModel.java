package com.vessel.gather.module.login.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.utils.HttpResultVoidFunc;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author vesselzhang
 * @date 2017/12/18
 */

@ActivityScope
public class PasswordModel extends BaseModel implements PasswordContract.Model {

    @Inject
    public PasswordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Boolean> sendSms(String phone) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .sendSms(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultVoidFunc());
    }

    @Override
    public Observable<Boolean> resetPassword(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .resetPassword(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultVoidFunc());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}