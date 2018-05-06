package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.module.me.di.CollectContract;
import com.vessel.gather.module.me.di.CollectPresenter;

import butterknife.BindView;

/**
 * @author vesselzhang
 * @date 2017/12/4
 */

public class CollectFragment extends MySupportFragment<CollectPresenter> implements CollectContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.tv_right)
    TextView mRightTV;

    public static CollectFragment newInstance() {
        Bundle args = new Bundle();

        CollectFragment fragment = new CollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_about, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("我的收藏");
        mRightTV.setVisibility(View.VISIBLE);
        mRightTV.setText("编辑");
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        ArmsUtils.makeText(getActivity(), message);
    }

    @Override
    public void launchActivity(Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
