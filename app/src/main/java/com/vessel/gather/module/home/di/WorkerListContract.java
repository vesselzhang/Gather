package com.vessel.gather.module.home.di;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.vessel.gather.app.base.MySupportAdapter;
import com.vessel.gather.app.data.entity.ServiceListResponse;
import com.vessel.gather.app.data.entity.TypeListResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public interface WorkerListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setAdapter(MySupportAdapter adapter);

        void updatePop(List<TypeListResponse.TypesBean> list, int index);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<ServiceListResponse> queryServiceList(Map<String, Object> map);

        Observable<TypeListResponse> queryTypeList(int parentType);
    }
}
