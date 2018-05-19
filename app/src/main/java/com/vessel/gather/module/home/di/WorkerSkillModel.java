package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.app.data.entity.VariableResponse;
import com.vessel.gather.app.utils.HttpResultFunc;
import com.vessel.gather.app.utils.HttpResultVoidFunc;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class WorkerSkillModel extends BaseModel implements WorkerSkillContract.Model {

    @Inject
    public WorkerSkillModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Boolean> saveSkill(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .saveSkill(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultVoidFunc());
    }

    @Override
    public Observable<Boolean> removeSkill(long skillId) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .removeSkill(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultVoidFunc());
    }

    @Override
    public Observable<TypeListResponse> queryTypeList(int parentType) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .queryTypeList(parentType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>());
    }

    @Override
    public Observable<VariableResponse> uploadFile(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .uploadFile(requestFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}