package com.vessel.gather.module.cart.di;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.app.data.entity.CartListResponse.CartsBean;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;
import com.vessel.gather.event.Event;
import com.vessel.gather.module.cart.adapter.CartAdapter;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.vessel.gather.event.Event.EVENT_CART_UPDATE;

@ActivityScope
public class CartPresenter extends BasePresenter<CartContract.Model, CartContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Gson gson;
    @Inject
    CartAdapter mAdapter;
    @Inject
    ArrayList<CartsBean> mList;

    @Inject
    public CartPresenter(CartContract.Model model, CartContract.View rootView) {
        super(model, rootView);
    }

    public void cartList() {
        String token = DataHelper.getStringSF(mAppManager.getCurrentActivity(), SPConstant.SP_TOKEN);
        if (TextUtils.isEmpty(token)) {
            return;
        }
        mModel.cartList()
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<CartsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(List<CartsBean> cartsBeans) {
                        mList.clear();
                        mList.addAll(cartsBeans);
                        mAdapter.notifyDataSetChanged();

                        EventBus.getDefault().post(new Event(), EVENT_CART_UPDATE);
                    }
                });
    }

    public void deleteCart() {
        StringBuilder cartIds = new StringBuilder();
        Iterator<CartsBean> iterator = mList.iterator();
        while (iterator.hasNext()) {
            CartsBean cartsBean = iterator.next();
            Iterator<CartsBean> iterator1 = cartsBean.getCartDetail().iterator();
            while (iterator1.hasNext()) {
                CartsBean detail = iterator1.next();
                if (detail.isSelected()) {
                    cartIds.append(detail.getCartId() + ",");
                }
            }
        }
        if (cartIds.length() == 0) {
            return;
        } else {
            cartIds.delete(cartIds.length() - 1, cartIds.length());
        }
        mModel.delCartById(cartIds.toString())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ProgressSubscriber<Boolean>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        mRootView.showMessage("删除成功");
                        cartList();
                    }
                });
    }

    public void submitOrder(String ids, int addressId) {
        mModel.submitOrder(ids, addressId)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ProgressSubscriber<Map<String, Integer>>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(Map<String, Integer> map) {
                        super.onNext(map);
                        if (map.containsKey("orderId")) {
                            ARouter.getInstance().build("/app/order/pay")
                                    .withSerializable(Constants.KEY_ORDER_ID, map.get("orderId"))
                                    .withSerializable(Constants.KEY_CART_LIST, mList)
                                    .navigation();
                        }
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