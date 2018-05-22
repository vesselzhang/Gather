package com.vessel.gather.module.home.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse.SkillsBean;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;
import com.vessel.gather.module.home.adapter.WorkerSkillAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class WorkerDetailPresenter extends BasePresenter<WorkerDetailContract.Model, WorkerDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    private WorkerSkillAdapter mAdapter;
    private List<SkillsBean> mSkill;

    @Inject
    public WorkerDetailPresenter(WorkerDetailContract.Model model, WorkerDetailContract.View rootView
            , WorkerSkillAdapter adapter, List<SkillsBean> skill) {
        super(model, rootView);
        this.mAdapter = adapter;
        this.mSkill = skill;
    }

    public void getArtisanDetail(long artisanId) {
        mModel.queryArtisanInfo(artisanId)
                .subscribe(new ErrorHandleSubscriber<ArtisanInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ArtisanInfoResponse artisanInfoResponse) {
                        mSkill.clear();
                        mSkill.addAll(artisanInfoResponse.getSkills());
                        mAdapter.notifyDataSetChanged();

                        mRootView.setData2View(artisanInfoResponse);
                    }
                });
    }

    public void collectOrCancel(long artisanIds, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("artisanIds", artisanIds);
        map.put("type", type);
        mModel.collectOrCancel(map).subscribe(
                new ProgressSubscriber<Boolean>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        super.onNext(aBoolean);
                        getArtisanDetail(artisanIds);
                    }
                }
        );
    }

    public void logoff() {
        mModel.logoff(2).subscribe(
                new ProgressSubscriber<Boolean>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        super.onNext(aBoolean);
                        mRootView.showMessage("注销成功");
                        mRootView.killMyself();
                    }
                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}