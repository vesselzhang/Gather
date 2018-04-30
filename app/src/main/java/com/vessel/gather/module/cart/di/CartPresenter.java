package com.vessel.gather.module.cart.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.vessel.gather.app.data.entity.CartListResponse.CartsBean;
import com.vessel.gather.event.Event;
import com.vessel.gather.module.cart.adapter.CartAdapter;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.vessel.gather.event.Event.EVENT_CART_UPDATE;

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
                List<CartsBean> newList = new ArrayList<>();
                CartsBean title1 = new CartsBean();
                title1.setTitle(true);
                title1.setShopName("店铺名");

                CartsBean title1Detail = new CartsBean();
                title1Detail.setNum(1);
                title1Detail.setPrice(40.5f);
                title1Detail.setProductName("商品名");
                title1.getCartDetail().add(title1Detail);

                CartsBean title1Detail2 = new CartsBean();
                title1Detail2.setNum(1);
                title1Detail2.setPrice(40.5f);
                title1Detail2.setProductName("商品名");
                title1.getCartDetail().add(title1Detail2);
                newList.add(title1);


                CartsBean title2 = new CartsBean();
                title2.setTitle(true);
                title2.setShopName("店铺名");

                CartsBean title2Detail = new CartsBean();
                title2Detail.setNum(1);
                title2Detail.setPrice(40.5f);
                title2Detail.setProductName("商品名");
                title2.getCartDetail().add(title2Detail);
                newList.add(title2);

                mList.clear();
                mList.addAll(newList);
                mAdapter.notifyDataSetChanged();

                EventBus.getDefault().post(new Event(), EVENT_CART_UPDATE);
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