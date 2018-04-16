package com.vessel.gather.module.login.di;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.event.Event;
import com.vessel.gather.app.data.entity.VariableResponse;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.vessel.gather.event.Event.EVENT_USERINFO;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    public void login(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            mRootView.showMessage("尚未输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mRootView.showMessage("尚未输入密码");
            return;
        }
        mModel.loginByPhone(phone, password)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(
                        new ErrorHandleSubscriber<VariableResponse>(mErrorHandler) {
                            @Override
                            public void onNext(VariableResponse variableResponse) {
                                mRootView.showMessage("登录成功");
                                DataHelper.setStringSF(mApplication, SPConstant.SP_TOKEN, variableResponse.getToken());
                                EventBus.getDefault().post(new Event(), EVENT_USERINFO);
                                mRootView.killMyself();
                            }
                        }
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}