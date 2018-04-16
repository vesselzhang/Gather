package com.vessel.gather.module.me.di;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;
import com.miguelbcr.ui.rx_paparazzo2.entities.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static android.app.Activity.RESULT_OK;

/**
 * @author vesselzhang
 * @date 2017/12/18
 */

@ActivityScope
public class SettingPresenter extends BasePresenter<SettingContract.Model, SettingContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private AppManager mAppManager;

    private String avatarUri;

    @Inject
    public SettingPresenter(SettingContract.Model model, SettingContract.View rootView
            , RxErrorHandler handler, Application application, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mAppManager = appManager;
    }

    public void pickAvatar() {
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(mAppManager.getCurrentActivity())
                .setTitle("选择图片来源")
                .setItems(items, (dialog, which) -> {
                    if (which == 0) {
                        RxPaparazzo.single(mAppManager.getCurrentActivity())
                                .usingGallery()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new AvatarPick());
                    } else {
                        RxPaparazzo.single(mAppManager.getCurrentActivity())
                                .usingCamera()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new AvatarPick());
                    }
                })
                .create().show();
    }

    public void setNickName(String original) {
        EditText et = new EditText(mAppManager.getCurrentActivity());
        et.setText(original);
        et.setSingleLine(true);
        new AlertDialog.Builder(mAppManager.getCurrentActivity())
                .setTitle("更新昵称")
                .setView(et)
                .setPositiveButton("确定", (dialog, which) -> {
                    String input = et.getText().toString();
                    if (input.equals("")) {
                        mRootView.showMessage("昵称不能为空！");
                    } else {
                        uploadNickName(input);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    public void setSex() {
        CharSequence[] items = {"男", "女"};
        new AlertDialog.Builder(mAppManager.getCurrentActivity())
                .setTitle("选择性别")
                .setItems(items, (dialog, which) -> {
                    if (which == 0) {
                        uploadSex(0);
                    } else {
                        uploadSex(1);
                    }
                })
                .create().show();
    }

    public void setSignature(String original) {
        EditText et = new EditText(mAppManager.getCurrentActivity());
        et.setText(original);
        et.setSingleLine(true);
        new AlertDialog.Builder(mAppManager.getCurrentActivity())
                .setTitle("更新个性签名")
                .setView(et)
                .setPositiveButton("确定", (dialog, which) -> {
                    String input = et.getText().toString();
                    if (input.equals("")) {
                        mRootView.showMessage("签名不能为空！");
                    } else {
                        uploadSignature(input);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void uploadFile(File file) {
        mModel.uploadFile(file).flatMap(variableResponse -> {
            avatarUri = variableResponse.getUrl();
            Map<String, Object> map = new HashMap<>();
            map.put("icon", variableResponse.getUrl());
            return mModel.updateInfo(map);
        }).subscribe(
                new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (!TextUtils.isEmpty(avatarUri)) {
                            mRootView.showAvatar(avatarUri);
                        }
                    }
                }
        );
    }

    private void uploadNickName(String nickName) {
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", nickName);
        mModel.updateInfo(map)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(
                        new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                mRootView.updateNickName(nickName);
                            }
                        }
                );
    }

    private void uploadSex(int sex) {
        Map<String, Object> map = new HashMap<>();
        map.put("sex", sex);
        mModel.updateInfo(map)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(
                        new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                mRootView.updateSex(sex);
                            }
                        }
                );
    }

    private void uploadSignature(String signature) {
        Map<String, Object> map = new HashMap<>();
        map.put("signature", signature);
        mModel.updateInfo(map)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(
                        new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                mRootView.uploadSignature(signature);
                            }
                        }
                );
    }

    private class AvatarPick implements Observer<Response<Activity, FileData>> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Response<Activity, FileData> activityFileDataResponse) {
            if (activityFileDataResponse.resultCode() != RESULT_OK) {
                mRootView.showMessage("您取消了获取图片");
                return;
            }

            uploadFile(activityFileDataResponse.data().getFile());
        }

        @Override
        public void onError(Throwable e) {
            Log.e("error", e.toString());
        }

        @Override
        public void onComplete() {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}