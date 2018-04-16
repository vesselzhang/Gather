package com.vessel.gather.module.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/app/order/pay")
public class PayActivity extends MySupportActivity {

    @BindView(R.id.tv_title)
    TextView mTitleTV;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.cart_activity_pay;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("支付订单");
    }

    @OnClick({R.id.iv_left})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
