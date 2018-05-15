package com.vessel.gather.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.module.home.di.WorkerContract;
import com.vessel.gather.module.home.di.WorkerPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class WorkerFragment extends MySupportFragment<WorkerPresenter> implements WorkerContract.View {

    @BindView(R.id.tv_title)
    TextView mTitle;

    public static WorkerFragment newInstance() {
        Bundle args = new Bundle();

        WorkerFragment fragment = new WorkerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        return inflater.inflate(R.layout.home_fragment_worker, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitle.setText("师傅详情");
    }

    @Override
    public void setData(@Nullable Object o) {

    }

    @OnClick({R.id.iv_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
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
    public void showMessage(String message) {
        ArmsUtils.makeText(getActivity(), message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}