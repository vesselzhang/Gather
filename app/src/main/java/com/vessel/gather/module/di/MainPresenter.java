package com.vessel.gather.module.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.app.data.entity.CheckVersionResponse;
import com.vessel.gather.app.data.entity.IndexResponse;
import com.vessel.gather.event.Event;
import com.vessel.gather.widght.CustomDialog;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.vessel.gather.event.Event.EVENT_DOWNLOAD_APK;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void getIndexInfo() {
        mModel.getIndexInfo()
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<IndexResponse>(mErrorHandler) {
                    @Override
                    public void onNext(IndexResponse indexResponse) {
                        EventBus.getDefault().post(indexResponse, Constants.TAG_BANNERS_REFRESH);
                    }
                });
    }

    public void checkVersion() {
        mModel.checkVersion().subscribe(
                new Observer<CheckVersionResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(CheckVersionResponse checkVersionResponse) {
                        Context activity = mAppManager.getCurrentActivity();
                        try {
                            PackageManager manager = activity.getPackageManager();
                            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
                            if (info.versionCode < checkVersionResponse.getVersionCode()) {
                                if (checkVersionResponse.getMust() == 1) {
                                    CustomDialog selfDialog = new CustomDialog(activity);
                                    selfDialog.setTitle("系统提示");
                                    selfDialog.setMessage("您的版本已经过于老旧, 为了更好的体验请更新最新版本。");
                                    selfDialog.setYesOnclickListener("确定", () -> {
                                        EventBus.getDefault().post(new Event(checkVersionResponse.getDownloadUrl()), EVENT_DOWNLOAD_APK);
                                        mRootView.showMessage("版本下载中, 请稍候...");
                                        selfDialog.dismiss();
                                    });
                                    selfDialog.setNoOnclickListener("取消", () -> {
                                        SharedPreferences.Editor editor = activity.getSharedPreferences("config", 0).edit();
                                        editor.putLong("updateTime", System.currentTimeMillis());
                                        editor.commit();
                                        selfDialog.dismiss();
                                    });
                                    selfDialog.show();
                                } else {
                                    CustomDialog selfDialog = new CustomDialog(activity);
                                    selfDialog.setTitle("系统提示");
                                    selfDialog.setMessage("检查到新版本" + checkVersionResponse.getVersionName() + "，立即更新？");
                                    selfDialog.setYesOnclickListener("确定", () -> {
                                        EventBus.getDefault().post(new Event(checkVersionResponse.getDownloadUrl()), EVENT_DOWNLOAD_APK);
                                        mRootView.showMessage("版本下载中, 请稍候...");
                                        selfDialog.dismiss();
                                    });
                                    selfDialog.setNoOnclickListener("取消", () -> {
                                        SharedPreferences.Editor editor = activity.getSharedPreferences("config", 0).edit();
                                        editor.putLong(SPConstant.SP_VERSION_CHECK_TIME, System.currentTimeMillis());
                                        editor.commit();
                                        selfDialog.dismiss();
                                    });
                                    selfDialog.show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}