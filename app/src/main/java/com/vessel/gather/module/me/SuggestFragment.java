package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.module.me.di.DaggerSuggestComponent;
import com.vessel.gather.module.me.di.SuggestContract;
import com.vessel.gather.module.me.di.SuggestModule;
import com.vessel.gather.module.me.di.SuggestPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/12/4
 */

public class SuggestFragment extends MySupportFragment<SuggestPresenter> implements SuggestContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.suggest_content)
    EditText content;

    public static SuggestFragment newInstance() {
        Bundle args = new Bundle();

        SuggestFragment fragment = new SuggestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerSuggestComponent
                .builder()
                .appComponent(appComponent)
                .suggestModule(new SuggestModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_suggest, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("建议与反馈");
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.iv_left, R.id.suggest_image_add, R.id.suggest_submit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                getActivity().finish();
                break;
            case R.id.suggest_submit:
                mPresenter.submit(content.getText().toString());
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
