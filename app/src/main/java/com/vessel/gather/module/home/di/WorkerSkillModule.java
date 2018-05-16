package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class WorkerSkillModule {
    private WorkerSkillContract.View view;

    public WorkerSkillModule(WorkerSkillContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WorkerSkillContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WorkerSkillContract.Model provideModel(WorkerSkillModel model) {
        return model;
    }
}