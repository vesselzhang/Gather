package com.vessel.gather.module.cart.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.vessel.gather.app.data.entity.CartListResponse.CartsBean;
import com.vessel.gather.module.cart.adapter.CartAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class CartPresenter extends BasePresenter<CartContract.Model, CartContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    CartAdapter mAdapter;
    @Inject
    List<CartsBean> mList;

    @Inject
    public CartPresenter(CartContract.Model model, CartContract.View rootView) {
        super(model, rootView);
    }

    public void cartList() {
        mModel.cartList().subscribe(new Observer<List<CartsBean>>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(List<CartsBean> cartsBeans) {
                mList.clear();
                mList.addAll(cartsBeans);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}