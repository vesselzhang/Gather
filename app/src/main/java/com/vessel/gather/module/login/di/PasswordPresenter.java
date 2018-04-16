package com.vessel.gather.module.login.di;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author vesselzhang
 * @date 2017/12/18
 */

@ActivityScope
public class PasswordPresenter extends BasePresenter<PasswordContract.Model, PasswordContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public PasswordPresenter(PasswordContract.Model model, PasswordContract.View rootView
            , RxErrorHandler handler, Application application, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mAppManager = appManager;
    }

    public void sendSms(String phone) {
        mModel.sendSms(phone)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(
                        new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                mRootView.showMessage("短信发送成功");
                            }
                        }
                );
    }

    public void submit(String phone, String code, String password, String repeat) {
        if (TextUtils.isEmpty(code)) {
            mRootView.showMessage("验证码不可为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mRootView.showMessage("密码不可为空");
            return;
        }
        if (TextUtils.isEmpty(repeat) || !password.equals(repeat)) {
            mRootView.showMessage("两次密码不一致");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        map.put("newPassword", password);
        mModel.resetPassword(map)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                               @Override
                               public void onNext(Boolean aBoolean) {
                                   mRootView.showMessage("修改成功, 请重新登录");
                                   DataHelper.clearShareprefrence(mApplication);
                                   ARouter.getInstance().build("/app/login").navigation();
                               }
                           }
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}