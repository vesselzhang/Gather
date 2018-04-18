package com.vessel.gather.module.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/29
 */

public class SafeFragment extends MySupportFragment {

    @BindView(R.id.tv_title)
    TextView mTitleTV;

    public static SafeFragment newInstance() {
        Bundle args = new Bundle();

        SafeFragment fragment = new SafeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_safe, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("账户与安全");
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.iv_left, R.id.safe_update_phone, R.id.safe_reset_password})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                pop();
                break;
            case R.id.safe_update_phone:
                ArmsUtils.makeText(getActivity(), "页面未设计");
                break;
            case R.id.safe_reset_password:
                ArmsUtils.makeText(getActivity(), "页面未设计");
                break;
        }
    }
}
