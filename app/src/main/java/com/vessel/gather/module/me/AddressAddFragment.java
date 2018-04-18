package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.data.entity.AddressResponse;
import com.vessel.gather.module.me.di.AddressEditContract;
import com.vessel.gather.module.me.di.AddressEditModule;
import com.vessel.gather.module.me.di.AddressEditPresenter;
import com.vessel.gather.module.me.di.DaggerAddressEditComponent;
import com.ywp.addresspickerlib.AddressPickerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/28
 */
public class AddressAddFragment extends MySupportFragment<AddressEditPresenter> implements AddressEditContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.address_edit_name)
    EditText nameET;
    @BindView(R.id.address_edit_phone)
    EditText phoneET;
    @BindView(R.id.address_edit_region)
    TextView regionET;
    @BindView(R.id.address_edit_street)
    TextView streetET;
    @BindView(R.id.address_edit_postcode)
    EditText postCodeET;
    @BindView(R.id.address_edit_address)
    EditText addressET;

    private AddressResponse.Address address;
    private PopupWindow popupWindow;

    public static AddressAddFragment newInstance(AddressResponse.Address address) {
        Bundle args = new Bundle();
        args.putSerializable("address", address);

        AddressAddFragment fragment = new AddressAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerAddressEditComponent
                .builder()
                .appComponent(appComponent)
                .addressEditModule(new AddressEditModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_address_add, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        address = (AddressResponse.Address) getArguments().getSerializable("address");
        if (address != null) {
            mTitleTV.setText("收货地址编辑");
            nameET.setText(address.getName());
            phoneET.setText(address.getPhone());
            regionET.setText(address.getRegion());
            streetET.setText(address.getStreet());
            postCodeET.setText(address.getPostcode());
            addressET.setText(address.getDetailed());
        } else {
            mTitleTV.setText("新增收货地址");
        }
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.iv_left, R.id.address_picker_layout, R.id.address_edit_submit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                killMyself();
                break;
            case R.id.address_picker_layout:
                showAddressPickerPop();
                break;
            case R.id.address_edit_submit:
                mPresenter.saveAddress((address == null ? -1 : address.getAddressId()), nameET.getText().toString(),
                        phoneET.getText().toString(), regionET.getText().toString(), streetET.getText().toString(),
                        postCodeET.getText().toString(), addressET.getText().toString());
                break;
        }
    }

    /**
     * 显示地址选择的pop
     */
    private void showAddressPickerPop() {
        View parent = ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
        popupWindow = new PopupWindow(getActivity());
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.widght_address_picker, null, false);
        AddressPickerView addressView = rootView.findViewById(R.id.apvAddress);
        addressView.setOnAddressPickerSure((address, provinceCode, cityCode, districtCode) -> {
            regionET.setText(address);
            popupWindow.dismiss();
        });
        popupWindow.setContentView(rootView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        ArmsUtils.makeText(getActivity(), message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        pop();
    }
}
