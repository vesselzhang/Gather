package com.vessel.gather.module.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;

import butterknife.OnClick;

public class CartTabFragment extends MySupportFragment {

    public static CartTabFragment newInstance() {
        Bundle args = new Bundle();

        CartTabFragment fragment = new CartTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_fragment, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick(R.id.pay)
    void OnClick() {
        ARouter.getInstance().build("/app/order/pay").navigation();
    }
}
