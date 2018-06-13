package com.vessel.gather.module.cart.di;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.vessel.gather.app.data.entity.CartListResponse.CartsBean;

import java.util.List;

import io.reactivex.Observable;

public interface CartContract {
    interface View extends IView {

    }

    interface Model extends IModel {
        Observable<List<CartsBean>> cartList();

        Observable<Boolean> delCartById(String cartIds);
    }
}
