package com.vessel.gather.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse.SkillsBean;
import com.vessel.gather.module.home.di.DaggerWorkerSkillComponent;
import com.vessel.gather.module.home.di.WorkerSkillContract;
import com.vessel.gather.module.home.di.WorkerSkillModule;
import com.vessel.gather.module.home.di.WorkerSkillPresenter;
import com.vessel.gather.widght.RoundImage;

import butterknife.BindView;
import butterknife.OnClick;

public class WorkerSkillFragment extends MySupportFragment<WorkerSkillPresenter> implements WorkerSkillContract.View {

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.worker_skill_name)
    EditText mName;
    @BindView(R.id.worker_skill_type)
    Spinner spType;
    @BindView(R.id.worker_skill_pic)
    RoundImage mPic;
    @BindView(R.id.worker_skill_describe)
    EditText mDescribe;
    @BindView(R.id.worker_skill_price)
    EditText mPrice;
    @BindView(R.id.worker_skill_unit)
    EditText mUnit;

    @BindView(R.id.worker_skill_cancel)
    TextView mCancel;
    @BindView(R.id.worker_skill_complete)
    TextView mComplete;

    private SkillsBean skill;
    private boolean editModel;

    public static WorkerSkillFragment newInstance(SkillsBean skill) {
        Bundle args = new Bundle();
        if (skill != null) {
            args.putSerializable("skill", skill);
        }

        WorkerSkillFragment fragment = new WorkerSkillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWorkerSkillComponent
                .builder()
                .appComponent(appComponent)
                .workerSkillModule(new WorkerSkillModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        return inflater.inflate(R.layout.home_fragment_worker_skill, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        skill = (SkillsBean) getArguments().getSerializable("skill");
        mTitle.setText("我的技能");

        if (skill == null) {
            mCancel.setText("取消");
            mComplete.setText("完成");
            mName.setEnabled(true);
            spType.setEnabled(true);
            mPic.setEnabled(true);
            mDescribe.setEnabled(true);
            mPrice.setEnabled(true);
            mUnit.setEnabled(true);
            editModel = true;
        } else {
            mCancel.setText("删除技能");
            mComplete.setText("开始编辑");

            mName.setText(skill.getSkillName());
            GlideArms.with(getActivity()).load(BuildConfig.APP_DOMAIN + skill.getSkillPic()).into(mPic);
            mDescribe.setText(skill.getRemark());
            mPrice.setText(skill.getPrice() + "");

            mName.setEnabled(false);
            spType.setEnabled(false);
            mPic.setEnabled(false);
            mDescribe.setEnabled(false);
            mPrice.setEnabled(false);
            mUnit.setEnabled(false);
            editModel = false;
        }

        mPresenter.queryTypeList();
    }

    @Override
    public void setData(@Nullable Object o) {

    }

    @OnClick({R.id.iv_left, R.id.worker_skill_pic, R.id.worker_skill_cancel, R.id.worker_skill_complete})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.worker_skill_pic:
                mPresenter.pickImage();
                break;
            case R.id.worker_skill_cancel:
                if (!editModel) {
                    mPresenter.deleteSkill(skill.getSkillId());
                } else {
                    killMyself();
                }
                break;
            case R.id.worker_skill_complete:
                if (!editModel) {
                    editModel = true;
                    mCancel.setText("取消");
                    mComplete.setText("完成");
                    mName.setEnabled(true);
                    spType.setEnabled(true);
                    mPic.setEnabled(true);
                    mDescribe.setEnabled(true);
                    mPrice.setEnabled(true);
                    mUnit.setEnabled(true);
                } else {
                    if (TextUtils.isEmpty(mName.getText().toString())) {
                        showMessage("请添加技能名称");
                        return;
                    }
                    if (TextUtils.isEmpty(mDescribe.getText().toString())) {
                        showMessage("请添加技能描述");
                        return;
                    }
                    if (TextUtils.isEmpty(mPrice.getText().toString())) {
                        showMessage("请添加价格");
                        return;
                    }
                    mPresenter.saveSkill(skill == null ? -1 : skill.getSkillId(), mName.getText().toString(), spType.getSelectedItemPosition(),
                            mDescribe.getText().toString(), mPrice.getText().toString(), mUnit.getText().toString());
                }
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
        pop();
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        spType.setAdapter(adapter);
    }

    @Override
    public void showImage(String imageUri) {
        GlideArms.with(getActivity()).load(BuildConfig.APP_DOMAIN + imageUri).into(mPic);
    }
}
