package com.vessel.gather.module.home.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.home.WorkerSkillFragment;

import dagger.Component;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
@Component(modules = WorkerSkillModule.class, dependencies = AppComponent.class)
public interface WorkerSkillComponent {
    void inject(WorkerSkillFragment activity);
}