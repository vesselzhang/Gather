package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.app.data.entity.UserInfoResponse;
import com.vessel.gather.module.me.di.DaggerSettingComponent;
import com.vessel.gather.module.me.di.SettingContract;
import com.vessel.gather.module.me.di.SettingModule;
import com.vessel.gather.module.me.di.SettingPresenter;
import com.vessel.gather.widght.RoundImage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/29
 */

public class SettingFragment extends MySupportFragment<SettingPresenter> implements SettingContract.View {
    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.userinfo_avatar)
    RoundImage mAvatarRV;
    @BindView(R.id.userinfo_nickname)
    TextView mNickNameTV;

    private UserInfoResponse userInfo;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerSettingComponent
                .builder()
                .appComponent(appComponent)
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_setting, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("设置");
        userInfo = DataHelper.getDeviceData(getActivity(), SPConstant.SP_USERINFO);
        if (userInfo == null) {
            return;
        }
        Glide.with(getActivity())
                .load(BuildConfig.APP_DOMAIN + userInfo.getIcon())
                .into(mAvatarRV);
        if (!TextUtils.isEmpty(userInfo.getNickname())) mNickNameTV.setText(userInfo.getNickname());
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.iv_left, R.id.userinfo_avatar_layout, R.id.userinfo_nickname_layout, R.id.userinfo_safe_layout,
            R.id.userinfo_logout, R.id.userinfo_seller_layout, R.id.userinfo_worker_layout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.userinfo_avatar_layout:
                mPresenter.pickAvatar();
                break;
            case R.id.userinfo_nickname_layout:
                mPresenter.setNickName(pickRealString(mNickNameTV.getText().toString()));
                break;
            case R.id.userinfo_safe_layout:
                start(SafeFragment.newInstance());
                break;
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.userinfo_logout:
                DataHelper.clearShareprefrence(getActivity());
                killMyself();
                break;
            case R.id.userinfo_seller_layout:
                start(SellerApplyFragment.newInstance());
                break;
            case R.id.userinfo_worker_layout:
                start(WorkerApplyFragment.newInstance());
                break;
        }
    }

    @Override
    public void showAvatar(String uri) {
        Glide.with(getActivity())
                .load(BuildConfig.APP_DOMAIN + uri)
                .into(mAvatarRV);
    }

    @Override
    public void updateNickName(String nickName) {
        mNickNameTV.setText(nickName);
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

    private String pickRealString(String str) {
        if (str.equals("未填写")) {
            return "";
        } else {
            return str;
        }
    }
}
