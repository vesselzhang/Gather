package com.vessel.gather.module.home.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.home.SellerListFragment;

import dagger.Component;

@ActivityScope
@Component(modules = SellerListModule.class, dependencies = AppComponent.class)
public interface SellerListComponent {
    void inject(SellerListFragment activity);
}