package com.vessel.gather.module.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse;
import com.vessel.gather.app.utils.CommonUtils;
import com.vessel.gather.app.utils.RecycleViewDivider;
import com.vessel.gather.module.home.adapter.WorkerSkillAdapter;
import com.vessel.gather.module.home.di.DaggerWorkerDetailComponent;
import com.vessel.gather.module.home.di.WorkerDetailContract;
import com.vessel.gather.module.home.di.WorkerDetailModule;
import com.vessel.gather.module.home.di.WorkerDetailPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vessel.gather.app.constant.Constants.DEFAULT_LONG;

public class WorkerDetailFragment extends MySupportFragment<WorkerDetailPresenter> implements WorkerDetailContract.View {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.worker_pic)
    ImageView mPic;
    @BindView(R.id.worker_name)
    TextView mName;
    @BindView(R.id.worker_address)
    TextView mAddress;
    @BindView(R.id.worker_phone)
    TextView mPhone;
    @BindView(R.id.worker_score)
    RatingBar mScore;
    @BindView(R.id.worker_skill_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.worker_call)
    Button mCall;

    @Inject
    WorkerSkillAdapter mAdapter;

    private long artisanId;
    private ArtisanInfoResponse artisanInfoResponse;

    public static WorkerDetailFragment newInstance(long artisanId) {
        Bundle args = new Bundle();
        args.putLong("artisanId", artisanId);

        WorkerDetailFragment fragment = new WorkerDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWorkerDetailComponent
                .builder()
                .appComponent(appComponent)
                .workerDetailModule(new WorkerDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        return inflater.inflate(R.layout.home_fragment_worker_detail, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        artisanId = getArguments().getLong("artisanId", DEFAULT_LONG);
        mTitle.setText("师傅详情");
        if (artisanId == DEFAULT_LONG) {
            showMessage("错误的参数");
            killMyself();
            return;
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL
                , 20, getResources().getColor(R.color.def_bg)));
        mPresenter.getArtisanDetail(artisanId);
    }

    @Override
    public void setData(@Nullable Object o) {

    }

    @OnClick({R.id.iv_left, R.id.worker_add_skill, R.id.worker_call})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.worker_add_skill:
                start(WorkerSkillFragment.newInstance());
                break;
            case R.id.worker_call:
                if (TextUtils.isEmpty(artisanInfoResponse.getPhone())) {
                    return;
                }
                PermissionUtil.callPhone(new PermissionUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess() {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + artisanInfoResponse.getPhone()));
                        startActivity(intent);
                    }

                    @Override
                    public void onRequestPermissionFailure(List<String> list) {
                        ArmsUtils.makeText(getActivity(), "权限被拒绝");
                    }

                    @Override
                    public void onRequestPermissionFailureWithAskNeverAgain(List<String> list) {

                    }
                }, new RxPermissions(getActivity()), ArmsUtils.obtainAppComponentFromContext(getActivity()).rxErrorHandler());
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

    @Override
    public void setData2View(ArtisanInfoResponse artisanInfoResponse) {
        this.artisanInfoResponse = artisanInfoResponse;

        mName.setText(artisanInfoResponse.getRealName());
        if (artisanInfoResponse.getAddress() == null || artisanInfoResponse.getAddress().equals("null") || artisanInfoResponse.getAddress().equals("")) {
            mAddress.setText("地址：暂无");
        } else {
            mAddress.setText("地址：" + artisanInfoResponse.getAddress());
        }

        if (artisanInfoResponse.getPhone() == null || artisanInfoResponse.getPhone().equals("null") || artisanInfoResponse.getPhone().equals("")) {
            mPhone.setText("电话：暂无");
        } else if (CommonUtils.isMobile(artisanInfoResponse.getPhone()) || CommonUtils.isFourMobile(artisanInfoResponse.getPhone()) || CommonUtils.isPhone(artisanInfoResponse.getPhone())) {
            int phoneLength = artisanInfoResponse.getPhone().length();
            if (phoneLength > 4) {
                String temp = artisanInfoResponse.getPhone().substring(0, phoneLength / 2 - 2) + "****" + artisanInfoResponse.getPhone().substring(phoneLength / 2 + 2, artisanInfoResponse.getPhone().length());
                mPhone.setText("电话：" + temp);
            } else {
                mPhone.setText("电话：" + artisanInfoResponse.getPhone());
            }
        } else {
            mPhone.setText("电话：暂无");
        }

        GlideArms.with(getActivity()).load(BuildConfig.APP_DOMAIN + artisanInfoResponse.getRealPhoto()).into(mPic);

        if (artisanInfoResponse.getScore() == 0) {
            mScore.setVisibility(View.GONE);
        } else {
            mScore.setVisibility(View.VISIBLE);
            mScore.setRating(artisanInfoResponse.getScore());
        }

        if (TextUtils.isEmpty(artisanInfoResponse.getPhone())) {
            mCall.setVisibility(View.GONE);
        } else {
            mCall.setVisibility(View.VISIBLE);
        }
    }
}