package com.vessel.gather.module.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.app.data.entity.CartListResponse.CartsBean;
import com.vessel.gather.module.cart.di.DaggerPayComponent;
import com.vessel.gather.module.cart.di.PayContract;
import com.vessel.gather.module.cart.di.PayModule;
import com.vessel.gather.module.cart.di.PayPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/app/order/pay")
public class PayActivity extends MySupportActivity<PayPresenter> implements PayContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.pay_money)
    TextView money;
    @BindView(R.id.pay_money_real)
    TextView realMoney;
    @BindView(R.id.pay_wechat)
    RadioButton wechat;
    @BindView(R.id.pay_alipay)
    RadioButton alipay;

    private int orderId;
    private ArrayList<CartsBean> mList;
    private float count;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerPayComponent
                .builder()
                .appComponent(appComponent)
                .payModule(new PayModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.cart_activity_pay;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        orderId = getIntent().getIntExtra(Constants.KEY_ORDER_ID, Constants.DEFAULT_INT);
        mList = (ArrayList<CartsBean>) getIntent().getSerializableExtra(Constants.KEY_CART_LIST);
        mTitleTV.setText("支付订单");
        if (mList == null || mList.size() == 0 || orderId == Constants.DEFAULT_INT) {
            finish();
            return;
        }

        for (CartsBean cartsBean : mList) {
            if (cartsBean.isTitle()) {
                for (CartsBean detail : cartsBean.getCartDetail()) {
                    if (detail.isSelected()) {
                        count += detail.getNum() * detail.getPrice();
                    }
                }
            }
        }
        money.setText("¥" + count);
        realMoney.setText("¥" + count);
        wechat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                alipay.setChecked(false);
            }
        });
        alipay.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                wechat.setChecked(false);
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.pay_submit})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.pay_submit:
                mPresenter.payOrder(orderId, wechat.isChecked() ? 1 : 2);
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
    public void showMessage(@NonNull String message) {
        ArmsUtils.makeText(this, message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {
        finish();
    }
}
