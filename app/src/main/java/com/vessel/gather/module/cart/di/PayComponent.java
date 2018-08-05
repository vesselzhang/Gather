package com.vessel.gather.module.cart.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.cart.PayActivity;

import dagger.Component;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
@Component(modules = PayModule.class, dependencies = AppComponent.class)
public interface PayComponent {
    void inject(PayActivity activity);
}