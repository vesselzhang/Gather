package com.vessel.gather.module.cart.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.cart.CartTabFragment;

import dagger.Component;

@ActivityScope
@Component(modules = CartModule.class, dependencies = AppComponent.class)
public interface CartComponent {
    void inject(CartTabFragment activity);
}