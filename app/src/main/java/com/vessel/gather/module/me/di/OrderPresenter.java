package com.vessel.gather.module.me.di;

import android.view.View;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.vessel.gather.app.data.entity.OrderListResponse;
import com.vessel.gather.app.data.entity.OrderListResponse.OrdersBean.OrderDetailBean;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;
import com.vessel.gather.module.me.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class OrderPresenter extends BasePresenter<OrderContract.Model, OrderContract.View> implements DefaultAdapter.OnRecyclerViewItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    List<OrderDetailBean> mList;
    @Inject
    OrderAdapter mAdapter;

    private OrderListResponse orderList;

    @Inject
    public OrderPresenter(OrderContract.Model model, OrderContract.View rootView) {
        super(model, rootView);
    }

    public void orderList() {
        mModel.orderList(1, 100)
                .map(orderListResponse -> {
                    orderList = orderListResponse;

                    List<OrderDetailBean> newList = new ArrayList<>();
                    for (OrderListResponse.OrdersBean ordersBean : orderListResponse.getOrders()) {
                        for (OrderDetailBean each : ordersBean.getOrderDetail()) {

                            OrderDetailBean titleTemp = null;
                            for (OrderDetailBean title : newList) {
                                if (title.getShopId() == each.getShopId()) {
                                    titleTemp = title;
                                    break;
                                }
                            }
                            if (titleTemp == null) {
                                titleTemp = new OrderDetailBean();
                                titleTemp.setTitle(true);
                                titleTemp.setShopName(each.getShopName());
                                newList.add(titleTemp);
                            }
                            titleTemp.getContent().add(each);
                        }
                    }
                    return newList;
                })
                .subscribe(new ProgressSubscriber<List<OrderDetailBean>>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(List<OrderDetailBean> orderDetailBeans) {
                        super.onNext(orderDetailBeans);
                        mRootView.hasData(orderDetailBeans.size() > 0);

                        mList.clear();
                        mList.addAll(orderDetailBeans);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClick(View view, int i, Object o, int i1) {
        mRootView.launchActivity(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}