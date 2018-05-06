package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.app.data.entity.OrderListResponse.OrdersBean.OrderDetailBean;
import com.vessel.gather.module.me.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class OrderModule {
    private OrderContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrderModule(OrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderContract.Model provideModel(OrderModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    List<OrderDetailBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    OrderAdapter provideAdapter(List<OrderDetailBean> list) {
        return new OrderAdapter(list);
    }
}