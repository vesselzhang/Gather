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
//    @BindView(R.id.userinfo_sex)
//    TextView mSexTV;
//    @BindView(R.id.userinfo_signature)
//    TextView mSignatureTV;
//    @BindView(R.id.userinfo_apply_business)
//    View mApplyBusiness;
//    @BindView(R.id.userinfo_apply_worker)
//    View mApplyWorker;

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
            showMessage("错误的传参");
            killMyself();
            return;
        }
        Glide.with(getActivity())
                .load(BuildConfig.APP_DOMAIN + userInfo.getIcon())
                .into(mAvatarRV);
        if (!TextUtils.isEmpty(userInfo.getNickname())) mNickNameTV.setText(userInfo.getNickname());
//        if (userInfo.getSex() == 0 || userInfo.getSex() == 1) mSexTV.setText(userInfo.getSex() == 1 ? "女" : "男");
//        if (!TextUtils.isEmpty(userInfo.getSignature())) mSignatureTV.setText(userInfo.getSignature());
//        mApplyBusiness.setVisibility(userInfo.getIsMerchant() == 1 ? View.GONE : View.VISIBLE);
//        mApplyWorker.setVisibility(userInfo.getIsArtisan() == 1 ? View.GONE : View.VISIBLE);
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
//            case R.id.userinfo_sex_layout:
//                mPresenter.setSex();
//                break;
//            case R.id.userinfo_signature_layout:
//                mPresenter.setSignature(pickRealString(mSignatureTV.getText().toString()));
//                break;
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
//            case R.id.userinfo_apply_business:
//                ARouter.getInstance().build("/app/apply")
//                        .withSerializable(TYPE, TYPE_APPLY_BUSINESS)
//                        .navigation();
//                break;
//            case R.id.userinfo_apply_worker:
//                ARouter.getInstance().build("/app/apply")
//                        .withSerializable(TYPE, TYPE_APPLY_WORKER)
//                        .navigation();
//                break;
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
    public void updateSex(int sex) {
//        mSexTV.setText(sex == 1 ? "女" : "男");
    }

    @Override
    public void updateNickName(String nickName) {
        mNickNameTV.setText(nickName);
    }

    @Override
    public void uploadSignature(String signature) {
//        mSignatureTV.setText(signature);
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
