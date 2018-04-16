package com.vessel.gather.module.me.di;

import android.app.Application;
import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.app.data.entity.AddressResponse;
import com.vessel.gather.module.me.adapter.AddressAdapter;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class AddressPresenter extends BasePresenter<AddressContract.Model, AddressContract.View>
        implements AddressAdapter.OnAddressClickListener {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private AppManager mAppManager;

    private AddressAdapter mAdapter;
    private List<AddressResponse.Address> mAddress;

    @Inject
    public AddressPresenter(AddressContract.Model model, AddressContract.View rootView
            , RxErrorHandler handler, Application application
            , AppManager appManager, List<AddressResponse.Address> list, AddressAdapter adapter) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mAppManager = appManager;
        this.mAdapter = adapter;
        this.mAddress = list;

        mAdapter.setOnAddressClickListener(this);
    }

    public void getAddressList() {
        mModel.addressList()
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(
                        new ErrorHandleSubscriber<AddressResponse>(mErrorHandler) {
                            @Override
                            public void onNext(AddressResponse addressResponse) {
                                mAddress.clear();
                                mAddress.addAll(addressResponse.getAddresses());
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                );
    }

    private void editAddress(AddressResponse.Address address) {
        mRootView.jumpToEdit(address);
    }

    private void deleteAddress(AddressResponse.Address address) {
        mModel.removeAddress(String.valueOf(address.getAddressId()))
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        mRootView.showMessage("删除成功！");
                        mAddress.remove(address);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void resetDefaultAddress(AddressResponse.Address address) {
        mModel.resetDefaultAddress(String.valueOf(address.getAddressId()))
                .flatMap(aBoolean -> {
                    if (!aBoolean) {
                        mRootView.showMessage("设置出错");
                        return null;
                    }
                    return mModel.addressList();
                })
                .subscribe(new ErrorHandleSubscriber<AddressResponse>(mErrorHandler) {
                    @Override
                    public void onNext(AddressResponse addressResponse) {
                        mAddress.clear();
                        mAddress.addAll(addressResponse.getAddresses());
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onItemClick(View view, int type, AddressResponse.Address address) {
        if (address == null) return;
        switch (type) {
            case 0:
                editAddress(address);
                break;
            case 1:
                deleteAddress(address);
                break;
            case 2:
                resetDefaultAddress(address);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.mAddress = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}