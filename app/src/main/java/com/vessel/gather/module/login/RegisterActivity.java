package com.vessel.gather.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.module.login.di.DaggerRegisterComponent;
import com.vessel.gather.module.login.di.RegisterContract;
import com.vessel.gather.module.login.di.RegisterModule;
import com.vessel.gather.module.login.di.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/29
 */
@Route(path = "/app/register")
public class RegisterActivity extends MySupportActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.tv_right)
    TextView mRightTV;
    @BindView(R.id.iv_left)
    View mLeftTV;
//    @BindView(R.id.register_code)
//    EditText code;
//    @BindView(R.id.register_password)
//    EditText password;
//    @BindView(R.id.register_password_repeat)
//    EditText repeat;

    private String phone;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.login_activity_register;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("注册");
        mLeftTV.setVisibility(View.VISIBLE);
        mRightTV.setText("登录");
        mRightTV.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_left, R.id.tv_right, R.id.register_submit})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.tv_right:
                ARouter.getInstance().build("/app/login").navigation();
                killMyself();
                break;
            case R.id.register_submit:
//                mPresenter.submit(phone, code.getText().toString(),
//                        password.getText().toString(), repeat.getText().toString());
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
