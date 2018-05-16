package com.vessel.gather.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportAdapter;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.data.entity.ServiceListResponse;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.module.home.di.DaggerWorkerListComponent;
import com.vessel.gather.module.home.di.WorkerListContract;
import com.vessel.gather.module.home.di.WorkerListModule;
import com.vessel.gather.module.home.di.WorkerListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WorkerListFragment extends MySupportFragment<WorkerListPresenter> implements WorkerListContract.View {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_right)
    TextView mTitleRight;
    @BindView(R.id.iv_right)
    ImageView mImageRight;

    @BindView(R.id.work_list_type)
    TextView mWorkListType;
    @BindView(R.id.work_list)
    ListView mWorkList;

    public static WorkerListFragment newInstance() {
        Bundle args = new Bundle();

        WorkerListFragment fragment = new WorkerListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWorkerListComponent
                .builder()
                .appComponent(appComponent)
                .workerListModule(new WorkerListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        return inflater.inflate(R.layout.home_fragment_worker_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitle.setText("精湛技工");
        mTitleRight.setText("分类");
        mTitleRight.setVisibility(View.VISIBLE);
        mImageRight.setVisibility(View.VISIBLE);
        mPresenter.queryTypeList();

        mWorkList.setOnItemClickListener(mPresenter);
    }

    @Override
    public void setData(@Nullable Object o) {

    }

    @Override
    public void setAdapter(MySupportAdapter adapter) {
        mWorkList.setAdapter(adapter);
    }

    @Override
    public void updatePop(List<TypeListResponse.TypesBean> list, int index) {
        mWorkListType.setText(list.get(index).getTypeName());
        mPresenter.getShopList(true);
    }

    @Override
    public void jumpWorkerPage(ServiceListResponse.ArtisansBean artisansBean) {
        start(WorkerDetailFragment.newInstance());
    }

    @OnClick({R.id.ll_right, R.id.iv_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_right:
                mPresenter.showPop(view);
                break;
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
