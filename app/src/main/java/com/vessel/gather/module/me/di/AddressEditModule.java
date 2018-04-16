package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class AddressEditModule {
    private AddressEditContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AddressEditModule(AddressEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddressEditContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AddressEditContract.Model provideModel(AddressEditModel model) {
        return model;
    }
}