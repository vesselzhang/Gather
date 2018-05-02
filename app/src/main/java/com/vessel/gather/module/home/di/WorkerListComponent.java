package com.vessel.gather.module.home.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.home.WorkerListFragment;

import dagger.Component;

@ActivityScope
@Component(modules = WorkerListModule.class, dependencies = AppComponent.class)
public interface WorkerListComponent {
    void inject(WorkerListFragment activity);
}