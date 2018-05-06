package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class CollectModule {
    private CollectContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CollectModule(CollectContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CollectContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CollectContract.Model provideModel(CollectModel model) {
        return model;
    }
}