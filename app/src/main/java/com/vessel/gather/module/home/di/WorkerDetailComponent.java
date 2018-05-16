package com.vessel.gather.module.home.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.home.WorkerDetailFragment;

import dagger.Component;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
@Component(modules = WorkerDetailModule.class, dependencies = AppComponent.class)
public interface WorkerDetailComponent {
    void inject(WorkerDetailFragment activity);
}