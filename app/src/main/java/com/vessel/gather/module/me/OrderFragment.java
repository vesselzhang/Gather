package com.vessel.gather.module.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/12/4
 */

public class OrderFragment extends MySupportFragment {

    @BindView(R.id.tv_title)
    TextView mTitleTV;

    public static OrderFragment newInstance() {
        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_order, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("我的订单");
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.iv_left, R.id.me_order_item1, R.id.me_order_item2})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;
            case R.id.me_order_item1:
            case R.id.me_order_item2:
                start(OrderDetailFragment.newInstance());
                break;
        }
    }
}