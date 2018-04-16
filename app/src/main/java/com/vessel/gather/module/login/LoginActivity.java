package com.vessel.gather.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.module.login.di.DaggerLoginComponent;
import com.vessel.gather.module.login.di.LoginContract;
import com.vessel.gather.module.login.di.LoginModule;
import com.vessel.gather.module.login.di.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/29
 */
@Route(path = "/app/login")
public class LoginActivity extends MySupportActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.iv_left)
    View mLeftTV;
    @BindView(R.id.login_account)
    EditText accountET;
    @BindView(R.id.login_password)
    EditText passwordET;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.login_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initToolBar();
    }

    private void initToolBar() {
        mTitleTV.setText("登录");
        mLeftTV.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_left, R.id.login_register, R.id.login_submit})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.login_register:
                ARouter.getInstance().build("/app/register").navigation();
                break;
            case R.id.login_submit:
                mPresenter.login(accountET.getText().toString(), passwordET.getText().toString());
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
        ArmsUtils.makeText(activity, message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        ARouter.getInstance().build("/app/main").navigation();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            killMyself();
        }
        return super.onKeyDown(keyCode, event);
    }
}
