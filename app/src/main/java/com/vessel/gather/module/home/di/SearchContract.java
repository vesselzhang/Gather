package com.vessel.gather.module.home.di;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.vessel.gather.app.data.entity.SearchResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public interface SearchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void jump2Worker(long artisanId);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<SearchResponse> searchInfo(@FieldMap Map<String, Object> map);
    }
}
