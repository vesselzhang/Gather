package com.vessel.gather.module.home.di;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.home.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class SearchModule {
    private SearchContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SearchModule(SearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchContract.Model provideModel(SearchModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    List<MultiItemEntity> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    SearchAdapter provideAdatper(List<MultiItemEntity> list) {
        return new SearchAdapter(list);
    }
}