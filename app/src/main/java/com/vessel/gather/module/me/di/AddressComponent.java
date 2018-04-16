package com.vessel.gather.module.me.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.me.AddressFragment;

import dagger.Component;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
@Component(modules = AddressModule.class, dependencies = AppComponent.class)
public interface AddressComponent {
    void inject(AddressFragment activity);
}