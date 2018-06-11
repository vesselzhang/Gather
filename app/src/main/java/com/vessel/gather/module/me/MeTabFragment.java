package com.vessel.gather.module.me;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.data.entity.CheckVersionResponse;
import com.vessel.gather.app.data.entity.UserInfoResponse;
import com.vessel.gather.app.utils.HttpResultFunc;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;
import com.vessel.gather.event.Event;
import com.vessel.gather.widght.CustomDialog;
import com.vessel.gather.widght.RoundImage;

import org.simple.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.vessel.gather.event.Event.EVENT_DOWNLOAD_APK;

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
    @BindView(R.id.me_seller)
    View sellerView;
    @BindView(R.id.me_worker)
    View workerView;

    private String token;
    private UserInfoResponse userInfo;
    private AppComponent mAppComponent;
    private static final String APP_ID = "wxd060871bdf1eb854";
    private IWXAPI api;

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
        regToWx(getContext());
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
            workerView.setVisibility(View.GONE);
//            sellerView.setVisibility(View.GONE);
            return;
        } else {
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
            orderView.setVisibility(View.VISIBLE);
            userInfo = DataHelper.getDeviceData(getActivity(), SPConstant.SP_USERINFO);
            if (userInfo != null) {
                mAppComponent.imageLoader()
                        .loadImage(getActivity(), ImageConfigImpl
                                .builder()
                                .imageView(avatar)
                                .url(BuildConfig.APP_DOMAIN + userInfo.getIcon())
                                .build());
                name.setText(userInfo.getNickname());
                if (DeviceUtils.getNetworkType(getContext()) != 0) {
                    workerView.setVisibility(userInfo.getIsArtisan() == 1 ? View.VISIBLE : View.GONE);
//                    sellerView.setVisibility(userInfo.getIsArtisan() == 1 ? View.VISIBLE : View.GONE);
                }
            }
            //再次获取UserInfo
            mAppComponent.repositoryManager().obtainRetrofitService(CommonService.class)
                    .queryInfo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new HttpResultFunc<>())
                    .compose(RxLifecycleUtils.bindToLifecycle(this))
                    .subscribe(new Observer<UserInfoResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UserInfoResponse userInfoResponse) {
                            userInfo = userInfoResponse;
                            DataHelper.saveDeviceData(getActivity(), SPConstant.SP_USERINFO, userInfoResponse);
                            mAppComponent.imageLoader()
                                    .loadImage(getActivity(), ImageConfigImpl
                                            .builder()
                                            .imageView(avatar)
                                            .url(BuildConfig.APP_DOMAIN + userInfoResponse.getIcon())
                                            .build());

                            name.setText(userInfoResponse.getNickname());
                            workerView.setVisibility(userInfoResponse.getIsArtisan() == 1 ? View.VISIBLE : View.GONE);
//                            sellerView.setVisibility(userInfoResponse.getIsArtisan() == 1 ? View.VISIBLE : View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            workerView.setVisibility(View.GONE);
//                            sellerView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @OnClick({R.id.me_order, R.id.me_seller, R.id.me_worker, R.id.me_collect, R.id.me_address, R.id.me_notepad,
            R.id.me_share, R.id.me_suggest, R.id.me_about, R.id.me_contact, R.id.me_update,
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
            case R.id.me_seller:
//                if (TextUtils.isEmpty(token)) {
//                    ARouter.getInstance().build("/app/login").navigation();
//                    return;
//                }
//                ARouter.getInstance().build("/app/container")
//                        .withSerializable(Constants.PAGE, Constants.PAGE_ORDER)
//                        .navigation();
                break;
            case R.id.me_worker:
                if (TextUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/app/login").navigation();
                    return;
                }
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_WORKER)
                        .withLong(Constants.KEY_WORKER_ID, userInfo.getArtisanId())
                        .navigation();
                break;
            case R.id.me_collect:
                if (TextUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/app/login").navigation();
                    return;
                }
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_COLLECT)
                        .navigation();
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
                shareToWx();
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
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18344601590"));
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
            case R.id.me_update:
                updateVersion();
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

    private void updateVersion() {
        mAppComponent.repositoryManager().obtainRetrofitService(CommonService.class)
                .checkVersion("Android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>())
                .compose(RxLifecycleUtils.bindToLifecycle(this))
                .subscribe(new ProgressSubscriber<CheckVersionResponse>(getActivity(), mAppComponent.rxErrorHandler()) {
                    @Override
                    public void onNext(CheckVersionResponse checkVersionResponse) {
                        super.onNext(checkVersionResponse);
                        try {
                            PackageManager manager = getActivity().getPackageManager();
                            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
                            if (info.versionCode < checkVersionResponse.getVersionCode()) {
                                CustomDialog selfDialog = new CustomDialog(getActivity());
                                selfDialog.setTitle("系统提示");
                                selfDialog.setMessage("检查到新版本" + checkVersionResponse.getVersionName() + "，立即更新？");
                                selfDialog.setYesOnclickListener("确定", () -> {
                                    EventBus.getDefault().post(new Event(checkVersionResponse.getDownloadUrl()), EVENT_DOWNLOAD_APK);
                                    ArmsUtils.makeText(getActivity(), "版本下载中, 请稍候...");
                                    selfDialog.dismiss();
                                });
                                selfDialog.setNoOnclickListener("取消", () -> {
                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("config", 0).edit();
                                    editor.putLong(SPConstant.SP_VERSION_CHECK_TIME, System.currentTimeMillis());
                                    editor.commit();
                                    selfDialog.dismiss();
                                });
                                selfDialog.show();
                            } else {
                                ArmsUtils.makeText(getActivity(), "已经是最新版本");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void regToWx(Context context) {
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        api.registerApp(APP_ID);
    }

    private void shareToWx() {
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = "http://www.baidu.com";

        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = "聚集title";
        msg.description = "网页描述";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        msg.thumbData = bitmap2Bytes(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "transaction";
        req.message = msg;
        req.scene = WXSceneSession;

        api.sendReq(req);
    }

    private byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
