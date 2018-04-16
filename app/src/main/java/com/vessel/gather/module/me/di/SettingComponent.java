package com.vessel.gather.module.me.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.me.MeSettingFragment;

import dagger.Component;

/**
 * @author vesselzhang
 * @date 2017/12/18
 */

@ActivityScope
@Component(modules = SettingModule.class, dependencies = AppComponent.class)
public interface SettingComponent {
    void inject(MeSettingFragment activity);
}