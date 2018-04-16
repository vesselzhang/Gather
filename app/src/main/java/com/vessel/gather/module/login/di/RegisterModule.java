package com.vessel.gather.module.login.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class RegisterModule {
    private RegisterContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RegisterModule(RegisterContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RegisterContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RegisterContract.Model provideModel(RegisterModel model) {
        return model;
    }
}