package com.vessel.gather.module.me.di;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;
import com.miguelbcr.ui.rx_paparazzo2.entities.Response;
import com.vessel.gather.app.data.entity.VariableResponse;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * @date 2017/12/5
 */

@ActivityScope
public class SuggestPresenter extends BasePresenter<SuggestContract.Model, SuggestContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    private String avatarUri;

    @Inject
    public SuggestPresenter(SuggestContract.Model model, SuggestContract.View rootView) {
        super(model, rootView);
    }

    public void submit(String str, String phone) {
        if (TextUtils.isEmpty(str)) {
            mRootView.showMessage("请先输入内容");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("content", str);
        if (!TextUtils.isEmpty(phone)) {
            map.put("phone", phone);
        }
        if (!TextUtils.isEmpty(avatarUri)) {
            List<String> jsonList = new ArrayList<>();
            jsonList.add(avatarUri);
            map.put("pics", new Gson().toJson(jsonList));
        }
        mModel.submitAdvice(map)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        mRootView.showMessage("提交成功");
                        mRootView.killMyself();
                    }
                });
    }

    private void uploadFile(File file) {
        mModel.uploadFile(file).subscribe(
                new ProgressSubscriber<VariableResponse>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(VariableResponse variableResponse) {
                        super.onNext(variableResponse);
                        avatarUri = variableResponse.getUrl();
                        mRootView.showImage(file);
                    }
                }
        );
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
    }
}