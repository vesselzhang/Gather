package com.vessel.gather.module.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.utils.RecycleViewDivider;
import com.vessel.gather.module.cart.adapter.CartAdapter;
import com.vessel.gather.module.cart.di.CartContract;
import com.vessel.gather.module.cart.di.CartModule;
import com.vessel.gather.module.cart.di.CartPresenter;
import com.vessel.gather.module.cart.di.DaggerCartComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CartTabFragment extends MySupportFragment<CartPresenter> implements CartContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.cart_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.cart_select_all)
    CheckBox selectAll;
    @BindView(R.id.cart_cost)
    TextView cost;

    @Inject
    CartAdapter mAdapter;

    public static CartTabFragment newInstance() {
        Bundle args = new Bundle();

        CartTabFragment fragment = new CartTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCartComponent
                .builder()
                .appComponent(appComponent)
                .cartModule(new CartModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_fragment, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("购物车");
        initRecycleView();


        mPresenter.cartList();
    }

    private void initRecycleView() {
        ArmsUtils.configRecyclerView(mRecyclerView, new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL
                , 20, getResources().getColor(R.color.def_bg)));
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick(R.id.cart_pay)
    void OnClick() {
        ARouter.getInstance().build("/app/order/pay").navigation();
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

    }

    @Override
    public void killMyself() {

    }
}
