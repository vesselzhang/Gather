package com.vessel.gather.module.bbs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;

import butterknife.BindView;

public class BbsTabFragment extends MySupportFragment {

    @BindView(R.id.tv_title)
    TextView mTitleTV;

    public static BbsTabFragment newInstance() {
        Bundle args = new Bundle();

        BbsTabFragment fragment = new BbsTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bbs_fragment, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("论坛");
    }

    @Override
    public void setData(Object data) {

    }
}
