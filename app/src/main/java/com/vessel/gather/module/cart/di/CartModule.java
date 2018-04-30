package com.vessel.gather.module.cart.di;

import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.app.data.entity.CartListResponse.CartsBean;
import com.vessel.gather.module.cart.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class CartModule {
    private CartContract.View view;

    public CartModule(CartContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CartContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CartContract.Model provideModel(CartModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    List<CartsBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    CartAdapter provideAdapter(List<CartsBean> list) {
        return new CartAdapter(list);
    }
}