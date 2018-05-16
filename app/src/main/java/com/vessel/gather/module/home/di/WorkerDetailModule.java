package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse;
import com.vessel.gather.module.home.adapter.WorkerSkillAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class WorkerDetailModule {
    private WorkerDetailContract.View view;

    public WorkerDetailModule(WorkerDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WorkerDetailContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WorkerDetailContract.Model provideModel(WorkerDetailModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    List<ArtisanInfoResponse.SkillsBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    WorkerSkillAdapter provideAdapter(List<ArtisanInfoResponse.SkillsBean> list){
        return new WorkerSkillAdapter(list);
    }
}