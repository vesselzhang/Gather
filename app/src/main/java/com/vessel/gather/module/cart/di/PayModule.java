package com.vessel.gather.module.cart.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class PayModule {
    private PayContract.View view;

    public PayModule(PayContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PayContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PayContract.Model provideModel(PayModel model) {
        return model;
    }
}