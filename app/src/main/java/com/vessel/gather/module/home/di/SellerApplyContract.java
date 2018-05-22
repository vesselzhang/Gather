package com.vessel.gather.module.home.di;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.app.data.entity.VariableResponse;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public interface SellerApplyContract {
    interface View extends IView {
        void setTypeAdapter(List<String> types);
        void showImage(int position, File file);
    }

    interface Model extends IModel {
        Observable<TypeListResponse> queryTypeList(int parentType);
        Observable<VariableResponse> uploadFile(File file);
        Observable<Boolean> authorityApply(Map<String, Object> map);
    }
}
