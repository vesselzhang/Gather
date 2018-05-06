package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.utils.RecycleViewDivider;
import com.vessel.gather.module.me.adapter.OrderAdapter;
import com.vessel.gather.module.me.di.DaggerOrderComponent;
import com.vessel.gather.module.me.di.OrderContract;
import com.vessel.gather.module.me.di.OrderModule;
import com.vessel.gather.module.me.di.OrderPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/12/4
 */

public class OrderFragment extends MySupportFragment<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.null_text)
    TextView mNullText;
    @BindView(R.id.order_list)
    RecyclerView mRecyclerView;

    @Inject
    OrderAdapter mAdapter;

    public static OrderFragment newInstance() {
        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerOrderComponent
                .builder()
                .appComponent(appComponent)
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_order, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("我的订单");

        initRecycleView();
        mPresenter.orderList();
    }

    @Override
    public void setData(Object data) {

    }

    private void initRecycleView() {
        mAdapter.setOnItemClickListener(mPresenter);
        ArmsUtils.configRecyclerView(mRecyclerView, new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL
                , 20, getResources().getColor(R.color.def_bg)));
    }

    @OnClick({R.id.iv_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String s) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        start(OrderDetailFragment.newInstance());
    }

    @Override
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void hasData(boolean hasData) {
        mNullText.setVisibility(hasData ? View.GONE : View.VISIBLE);
    }
}