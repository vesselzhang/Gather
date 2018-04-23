package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class SellerListModule {
    private SellerListContract.View view;

    public SellerListModule(SellerListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SellerListContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SellerListContract.Model provideModel(SellerListModel model) {
        return model;
    }
}