package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.app.data.entity.NotePadResponse;
import com.vessel.gather.module.me.adapter.MemoAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class MemoModule {
    private MemoContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MemoModule(MemoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MemoContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MemoContract.Model provideModel(MemoModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    List<NotePadResponse.NotesBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    MemoAdapter provideAdapter(List<NotePadResponse.NotesBean> list) {
        return new MemoAdapter(list);
    }
}