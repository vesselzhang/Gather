package com.vessel.gather.module.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.MainActivity;

import dagger.Component;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}