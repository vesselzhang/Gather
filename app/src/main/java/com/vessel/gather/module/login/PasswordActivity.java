package com.vessel.gather.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.module.login.di.DaggerPasswordComponent;
import com.vessel.gather.module.login.di.PasswordContract;
import com.vessel.gather.module.login.di.PasswordModule;
import com.vessel.gather.module.login.di.PasswordPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/12/18
 */
@Route(path = "/app/password")
public class PasswordActivity extends MySupportActivity<PasswordPresenter> implements PasswordContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.iv_left)
    View mLeftTV;
    @BindView(R.id.password_code)
    EditText code;
    @BindView(R.id.password_password)
    EditText password;
    @BindView(R.id.password_repeat)
    EditText repeat;

    private String phone;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerPasswordComponent
                .builder()
                .appComponent(appComponent)
                .passwordModule(new PasswordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.login_activity_password;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("修改密码");
        phone = getIntent().getStringExtra("phone");
        if (TextUtils.isEmpty(phone)) {
            showMessage("错误的参数");
            killMyself();
            return;
        }
        mPresenter.sendSms(phone);
    }

    @OnClick({R.id.iv_left, R.id.tv_right, R.id.password_submit})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.password_submit:
                mPresenter.submit(phone, code.getText().toString(),
                        password.getText().toString(), repeat.getText().toString());
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
        finish();
    }
}
