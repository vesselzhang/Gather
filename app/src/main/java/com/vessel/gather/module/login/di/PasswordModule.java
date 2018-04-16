package com.vessel.gather.module.login.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/12/18
 */

@Module
public class PasswordModule {
    private PasswordContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PasswordModule(PasswordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PasswordContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PasswordContract.Model provideModel(PasswordModel model) {
        return model;
    }
}