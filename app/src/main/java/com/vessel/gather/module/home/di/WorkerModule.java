package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class WorkerModule {
    private WorkerContract.View view;

    public WorkerModule(WorkerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WorkerContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WorkerContract.Model provideModel(WorkerModel model) {
        return model;
    }
}