package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/12/18
 */

@Module
public class SettingModule {
    private SettingContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SettingModule(SettingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SettingContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SettingContract.Model provideModel(SettingModel model) {
        return model;
    }
}