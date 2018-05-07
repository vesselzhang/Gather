package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.module.me.di.DaggerSellerApplyComponent;
import com.vessel.gather.module.me.di.SellerApplyContract;
import com.vessel.gather.module.me.di.SellerApplyModule;
import com.vessel.gather.module.me.di.SellerApplyPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    Spinner spSex;
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


    @BindView(R.id.seller_apply_zhengmian_temp)
    View zhengmianLayout;
    @BindView(R.id.seller_apply_zhengmian_image)
    ImageView zhengmianImage;
    @BindView(R.id.seller_apply_beimian_temp)
    View beimianLayout;
    @BindView(R.id.seller_apply_beimian_image)
    ImageView beimianImage;
    @BindView(R.id.seller_apply_zhizhao_temp)
    View zhizhaoLayout;
    @BindView(R.id.seller_apply_zhizhao_image)
    ImageView zhizhaoImage;

    private List<String> sex = new ArrayList<>();

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
        sex.clear();
        sex.add("男");
        sex.add("女");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sex);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spSex.setAdapter(adapter);

        mPresenter.queryTypeList();
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.et_seller_apply_submit, R.id.iv_left, R.id.seller_apply_zhengmian, R.id.seller_apply_beimian, R.id.seller_apply_zhizhao})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.seller_apply_zhengmian:
                mPresenter.pickAvatar(0);
                break;
            case R.id.seller_apply_beimian:
                mPresenter.pickAvatar(1);
                break;
            case R.id.seller_apply_zhizhao:
                mPresenter.pickAvatar(2);
                break;
            case R.id.et_seller_apply_submit:
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "姓名必填");
                    return;
                }
                if (TextUtils.isEmpty(etServiceCity.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "服务城市必填");
                    return;
                }
                if (TextUtils.isEmpty(etTongyiCode.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "统一码必填");
                    return;
                }
                mPresenter.authorityApply(etName.getText().toString(), (String) spSex.getSelectedItem(), etServiceCity.getText().toString()
                        , etAddress.getText().toString(), spType.getSelectedItemPosition(), etTongyiCode.getText().toString());
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
        ArmsUtils.makeText(getActivity(), s);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void setTypeAdapter(List<String> types) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spType.setAdapter(adapter);
    }

    @Override
    public void showImage(int position, File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (getActivity() == null) {
            return;
        }
        switch (position) {
            case 0:
                zhengmianLayout.setVisibility(View.GONE);
                GlideArms.with(getActivity())
                        .load(file)
                        .into(zhengmianImage);
                break;
            case 1:
                beimianLayout.setVisibility(View.GONE);
                GlideArms.with(getActivity())
                        .load(file)
                        .into(beimianImage);
                break;
            case 2:
                zhizhaoLayout.setVisibility(View.GONE);
                GlideArms.with(getActivity())
                        .load(file)
                        .into(zhizhaoImage);
                break;
        }
    }
}
