package com.vessel.gather.module.home.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.home.WorkerFragment;

import dagger.Component;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
@Component(modules = WorkerModule.class, dependencies = AppComponent.class)
public interface WorkerComponent {
    void inject(WorkerFragment activity);
}