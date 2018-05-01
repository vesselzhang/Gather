package com.vessel.gather.module.me.di;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.vessel.gather.app.data.entity.VariableResponse;

import java.io.File;

import io.reactivex.Observable;

/**
 * @author vesselzhang
 * @date 2017/12/5
 */

public interface SuggestContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void showImage(String url);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Boolean> submitAdvice(String content);

        Observable<VariableResponse> uploadFile(File file);
    }
}
