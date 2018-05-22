package com.vessel.gather.module.home.di;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public interface WorkerDetailContract {

    interface View extends IView {
        void setData2View(ArtisanInfoResponse artisanInfoResponse);
    }

    interface Model extends IModel {
        Observable<ArtisanInfoResponse> queryArtisanInfo(long artisanId);
        Observable<Boolean> collectOrCancel(Map<String, Object> map);
        Observable<Boolean> logoff(int type);
    }
}
