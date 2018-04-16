package com.vessel.gather.module.me.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.me.SuggestFragment;

import dagger.Component;

/**
 * @author vesselzhang
 * @date 2017/12/5
 */

@ActivityScope
@Component(modules = SuggestModule.class, dependencies = AppComponent.class)
public interface SuggestComponent {
    void inject(SuggestFragment activity);
}