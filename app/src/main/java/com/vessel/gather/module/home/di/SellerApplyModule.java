package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class SellerApplyModule {
    private SellerApplyContract.View view;

    public SellerApplyModule(SellerApplyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SellerApplyContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SellerApplyContract.Model provideModel(SellerApplyModel model) {
        return model;
    }
}