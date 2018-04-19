package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.data.entity.AddressResponse;
import com.vessel.gather.app.utils.RecycleViewDivider;
import com.vessel.gather.event.Event;
import com.vessel.gather.module.me.adapter.AddressAdapter;
import com.vessel.gather.module.me.di.AddressContract;
import com.vessel.gather.module.me.di.AddressModule;
import com.vessel.gather.module.me.di.AddressPresenter;
import com.vessel.gather.module.me.di.DaggerAddressComponent;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vessel.gather.event.Event.EVENT_ADDRESS_UPDATE;

/**
 * @author vesselzhang
 * @date 2017/11/28
 */

public class AddressFragment extends MySupportFragment<AddressPresenter> implements AddressContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    AddressAdapter mAdapter;

    public static AddressFragment newInstance() {
        Bundle args = new Bundle();

        AddressFragment fragment = new AddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerAddressComponent
                .builder()
                .appComponent(appComponent)
                .addressModule(new AddressModule(this))
                .build()
                .inject(this);

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_address, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("地址管理");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL
                , 20, getResources().getColor(R.color.def_bg)));

        updateList(null);
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.me_address_add, R.id.iv_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_address_add:
                start(AddressAddFragment.newInstance(null));
                break;
            case R.id.iv_left:
                killMyself();
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
        ArmsUtils.makeText(getActivity(), message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void jumpToEdit(AddressResponse.Address address) {
        start(AddressAddFragment.newInstance(address));
    }

    @Subscriber(tag = EVENT_ADDRESS_UPDATE)
    void updateList(Event event) {
        mPresenter.getAddressList();
    }
}
