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
 * @date 2017/11/29
 */

public class SellerApplyFragment extends MySupportFragment {

    @BindView(R.id.tv_title)
    TextView mTitleTV;

    public static SellerApplyFragment newInstance() {
        Bundle args = new Bundle();

        SellerApplyFragment fragment = new SellerApplyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_seller_apply, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("商家入驻");
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.et_seller_apply_submit, R.id.iv_left})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                pop();
                break;
            case R.id.et_seller_apply_submit:
                break;
        }
    }
}
