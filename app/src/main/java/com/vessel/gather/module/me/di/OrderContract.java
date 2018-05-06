package com.vessel.gather.module.me.di;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.vessel.gather.app.data.entity.OrderListResponse;

import io.reactivex.Observable;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public interface OrderContract {
    interface View extends IView {
        void hasData(boolean hasData);
    }

    interface Model extends IModel {
        Observable<OrderListResponse> orderList(int page, int pageSize);
    }
}
