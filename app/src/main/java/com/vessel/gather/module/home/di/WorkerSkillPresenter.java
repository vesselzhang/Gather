package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class WorkerSkillPresenter extends BasePresenter<WorkerSkillContract.Model, WorkerSkillContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public WorkerSkillPresenter(WorkerSkillContract.Model model, WorkerSkillContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}