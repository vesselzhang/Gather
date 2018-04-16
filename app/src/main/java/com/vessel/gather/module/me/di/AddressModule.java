package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.app.data.entity.AddressResponse;
import com.vessel.gather.module.me.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@Module
public class AddressModule {
    private AddressContract.View view;

    /**
     * 构建Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AddressModule(AddressContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddressContract.View provideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AddressContract.Model provideModel(AddressModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    List<AddressResponse.Address> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    AddressAdapter provideAdapter(List<AddressResponse.Address> list){
        return new AddressAdapter(list);
    }
}