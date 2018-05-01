package com.vessel.gather.module.me;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.data.entity.UserInfoResponse;
import com.vessel.gather.app.utils.HttpResultFunc;
import com.vessel.gather.widght.RoundImage;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MeTabFragment extends MySupportFragment {

    @BindView(R.id.me_login_layout)
    View login;
    @BindView(R.id.me_logout_layout)
    View logout;
    @BindView(R.id.me_userinfo_avatar)
    RoundImage avatar;
    @BindView(R.id.me_userinfo_name)
    TextView name;
    @BindView(R.id.me_order)
    View orderView;

    private String token;
    private AppComponent mAppComponent;

    public static MeTabFragment newInstance() {
        Bundle args = new Bundle();

        MeTabFragment fragment = new MeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(getActivity());
        if (TextUtils.isEmpty(token)) {
            token = DataHelper.getStringSF(getActivity(), SPConstant.SP_TOKEN);
        }
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onResume() {
        super.onResume();
        token = DataHelper.getStringSF(getActivity(), SPConstant.SP_TOKEN);
        if (TextUtils.isEmpty(token)) {
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            orderView.setVisibility(View.GONE);
            Glide.with(getActivity())
                    .load(BuildConfig.APP_DOMAIN)
                    .into(avatar);
            name.setText("");
            return;
        } else {
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
            orderView.setVisibility(View.VISIBLE);
            UserInfoResponse userInfo = DataHelper.getDeviceData(getActivity(), SPConstant.SP_USERINFO);
            if (userInfo != null) {
                mAppComponent.imageLoader()
                        .loadImage(getActivity(), ImageConfigImpl
                                .builder()
                                .imageView(avatar)
                                .url(BuildConfig.APP_DOMAIN + userInfo.getIcon())
                                .build());
                name.setText(userInfo.getNickname());
                if (DeviceUtils.getNetworkType(getContext()) != 0) {

                }
            }
            //再次获取UserInfo
            mAppComponent.repositoryManager().obtainRetrofitService(CommonService.class)
                    .queryInfo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new HttpResultFunc<>())
                    .subscribe(new Observer<UserInfoResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UserInfoResponse userInfoResponse) {
                            DataHelper.saveDeviceData(getActivity(), SPConstant.SP_USERINFO, userInfoResponse);
                            mAppComponent.imageLoader()
                                    .loadImage(getActivity(), ImageConfigImpl
                                            .builder()
                                            .imageView(avatar)
                                            .url(BuildConfig.APP_DOMAIN + userInfoResponse.getIcon())
                                            .build());

                            name.setText(userInfoResponse.getNickname());
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @OnClick({R.id.me_order, R.id.me_collect, R.id.me_address, R.id.me_notepad,
            R.id.me_share, R.id.me_suggest, R.id.me_about, R.id.me_contact,
            R.id.me_logout_layout, R.id.me_login_layout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_order:
                if (TextUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/app/login").navigation();
                    return;
                }
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_ORDER)
                        .navigation();
                break;
            case R.id.me_collect:
                if (TextUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/app/login").navigation();
                    return;
                }
                break;
            case R.id.me_address:
                if (TextUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/app/login").navigation();
                    return;
                }
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_ADDRESS)
                        .navigation();
                break;
            case R.id.me_notepad:
                if (TextUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/app/login").navigation();
                    return;
                }
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_MEMO)
                        .navigation();
                break;
            case R.id.me_share:
                break;
            case R.id.me_suggest:
                if (TextUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/app/login").navigation();
                    return;
                }
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_SUGGEST)
                        .navigation();
                break;
            case R.id.me_about:
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_ABOUT)
                        .navigation();
                break;
            case R.id.me_contact:
                PermissionUtil.callPhone(new PermissionUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess() {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "13182716780"));
                        startActivity(intent);
                    }

                    @Override
                    public void onRequestPermissionFailure(List<String> list) {
                        ArmsUtils.makeText(getActivity(), "权限被拒绝");
                    }

                    @Override
                    public void onRequestPermissionFailureWithAskNeverAgain(List<String> list) {

                    }
                }, new RxPermissions(getActivity()), mAppComponent.rxErrorHandler());
                break;
            case R.id.me_logout_layout:
                ARouter.getInstance().build("/app/login").navigation();
                break;
            case R.id.me_login_layout:
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_SETTING)
                        .navigation();
                break;
        }
    }
}
