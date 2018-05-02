package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class WorkerListModule {
    private WorkerListContract.View view;

    public WorkerListModule(WorkerListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WorkerListContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WorkerListContract.Model provideModel(WorkerListModel model) {
        return model;
    }
}