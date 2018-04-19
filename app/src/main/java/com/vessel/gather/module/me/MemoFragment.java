package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.module.me.adapter.MemoAdapter;
import com.vessel.gather.module.me.di.DaggerMemoComponent;
import com.vessel.gather.module.me.di.MemoContract;
import com.vessel.gather.module.me.di.MemoModule;
import com.vessel.gather.module.me.di.MemoPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/29
 */

public class MemoFragment extends MySupportFragment<MemoPresenter> implements MemoContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.tv_right)
    TextView mTitleRightTV;

    @BindView(R.id.no_data)
    View noData;
    @BindView(R.id.memo_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.memo_add)
    View addLayout;
    @BindView(R.id.memo_title)
    EditText title;
    @BindView(R.id.memo_time)
    TextView time;
    @BindView(R.id.memo_content)
    EditText content;

    @Inject
    MemoAdapter mAdapter;

    public static MemoFragment newInstance() {
        Bundle args = new Bundle();

        MemoFragment fragment = new MemoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMemoComponent
                .builder()
                .appComponent(appComponent)
                .memoModule(new MemoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_memo, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("记事本");
        mTitleRightTV.setVisibility(View.VISIBLE);
        mTitleRightTV.setText("添加");

        mAdapter.setOnDeleteClickListener(mPresenter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.queryNotepad();
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.iv_left, R.id.tv_right, R.id.memo_cancel, R.id.memo_save})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.tv_right:
                mTitleRightTV.setVisibility(View.GONE);
                addLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.memo_cancel:
                mTitleRightTV.setVisibility(View.VISIBLE);
                addLayout.setVisibility(View.GONE);
                break;
            case R.id.memo_save:
                mPresenter.saveNotepad(title.getText().toString(), time.getText().toString(), content.getText().toString());
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
        ArmsUtils.snackbarText(s);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void hasResult(boolean hasResult) {
        noData.setVisibility(hasResult ? View.GONE : View.VISIBLE);
    }

    @Override
    public void saveSuccess() {
        mTitleRightTV.setVisibility(View.VISIBLE);
        addLayout.setVisibility(View.GONE);
        title.setText("");
        content.setText("");
    }
}
