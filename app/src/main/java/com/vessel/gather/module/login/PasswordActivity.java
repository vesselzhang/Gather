package com.vessel.gather.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.module.login.di.DaggerPasswordComponent;
import com.vessel.gather.module.login.di.PasswordContract;
import com.vessel.gather.module.login.di.PasswordModule;
import com.vessel.gather.module.login.di.PasswordPresenter;

import java.util.regex.Pattern;

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
    @BindView(R.id.tv_right)
    TextView mRightTV;
    @BindView(R.id.iv_left)
    View mLeftTV;

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_password_repeat)
    EditText et_password_repeat;

    private String REGEX_MOBILE = "^((1[3,4,5,7,8,9]))\\d{9}$";

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
        mTitleTV.setText("重置密码");
        mLeftTV.setVisibility(View.VISIBLE);
        mRightTV.setText("登录");
        mRightTV.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_left, R.id.tv_right, R.id.password_btn_sendmsg, R.id.password_btn_submit})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.tv_right:
                ARouter.getInstance().build("/app/login").navigation();
                killMyself();
                break;
            case R.id.password_btn_sendmsg:
                String str = et_phone.getText().toString();
                if (!Pattern.matches(REGEX_MOBILE, str)) {
                    showMessage("请输入正确的手机号码");
                    return;
                }
                mPresenter.sendSms(str);
                break;
            case R.id.password_btn_submit:
                mPresenter.submit(et_phone.getText().toString(), et_code.getText().toString(),
                        et_password.getText().toString(), et_password_repeat.getText().toString());
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
