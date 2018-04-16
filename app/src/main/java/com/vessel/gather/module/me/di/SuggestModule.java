package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/12/5
 */

@Module
public class SuggestModule {
    private SuggestContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SuggestModule(SuggestContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SuggestContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SuggestContract.Model provideModel(SuggestModel model) {
        return model;
    }
}