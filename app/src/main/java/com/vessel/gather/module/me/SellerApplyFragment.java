package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.module.me.di.DaggerSellerApplyComponent;
import com.vessel.gather.module.me.di.SellerApplyContract;
import com.vessel.gather.module.me.di.SellerApplyModule;
import com.vessel.gather.module.me.di.SellerApplyPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/29
 */

public class SellerApplyFragment extends MySupportFragment<SellerApplyPresenter> implements SellerApplyContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.et_seller_apply_name)
    EditText etName;
    @BindView(R.id.et_seller_apply_sex)
    EditText etSex;
//    @BindView(R.id.et_seller_apply_id_card)
//    EditText etIdCard;
//    @BindView(R.id.et_seller_apply_id_card_address)
//    EditText etIdCardAddress;
    @BindView(R.id.et_seller_apply_service_city)
    EditText etServiceCity;
    @BindView(R.id.et_seller_apply_address)
    EditText etAddress;
    @BindView(R.id.et_seller_apply_type)
    Spinner spType;
    @BindView(R.id.et_seller_apply_tongyi_code)
    EditText etTongyiCode;

    public static SellerApplyFragment newInstance() {
        Bundle args = new Bundle();

        SellerApplyFragment fragment = new SellerApplyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerSellerApplyComponent
                .builder()
                .appComponent(appComponent)
                .sellerApplyModule(new SellerApplyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_seller_apply, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("商家入驻");
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.et_seller_apply_submit, R.id.iv_left})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                pop();
                break;
            case R.id.et_seller_apply_submit:
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
    public void showMessage(@NonNull String s) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
