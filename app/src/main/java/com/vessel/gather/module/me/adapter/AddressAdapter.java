package com.vessel.gather.module.me.adapter;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.vessel.gather.R;
import com.vessel.gather.app.data.entity.AddressResponse;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

public class AddressAdapter extends DefaultAdapter<AddressResponse.Address> {

    private OnAddressClickListener onAddressClickListener;

    public AddressAdapter(List<AddressResponse.Address> infos) {
        super(infos);
    }

    public void setOnAddressClickListener(OnAddressClickListener onAddressClickListener) {
        this.onAddressClickListener = onAddressClickListener;
    }

    @Override
    public BaseHolder<AddressResponse.Address> getHolder(View v, int viewType) {
        return new AddressHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_me_address;
    }

    class AddressHolder extends BaseHolder<AddressResponse.Address> {

        @BindView(R.id.rl_container) AutoLinearLayout mLayout;
        @BindView(R.id.item_address_name) TextView mName;
        @BindView(R.id.item_address_phone) TextView mPhone;
        @BindView(R.id.item_address_detailed) TextView mDetailed;
        @BindView(R.id.item_address_default) RadioButton mDefault;
        @BindView(R.id.item_address_delete) TextView mDelete;

        public AddressHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(AddressResponse.Address data, int position) {
            Observable.just(data).subscribe(address -> {
                mName.setText(address.getName());
                mPhone.setText(address.getPhone());
                mDetailed.setText(address.getRegion() + " " +  address.getStreet() + " " + address.getDetailed());
                mDefault.setChecked(address.getIsDefault() == 1);
                mDefault.setOnCheckedChangeListener((compoundButton, b) -> {
                    if (b) {
                        onAddressClickListener.onItemClick(compoundButton, 2, data);
                    }
                });
                mLayout.setOnClickListener(view -> {
                    if (onAddressClickListener != null) {
                        onAddressClickListener.onItemClick(view, 0, data);
                    }
                });
                mDelete.setOnClickListener(view -> {
                    if (onAddressClickListener != null) {
                        onAddressClickListener.onItemClick(view, 1, data);
                    }
                });
            });
        }
    }

    public interface OnAddressClickListener {
        /**
         * Address界面下的点击事件
         *
         * @param view 点击的view
         * @param type 0为编辑，1为删除，2设置为默认地址
         * @param address 点击的地址信息
         */
        void onItemClick(View view, int type, AddressResponse.Address address);
    }
}
