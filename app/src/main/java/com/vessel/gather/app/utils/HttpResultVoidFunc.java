package com.vessel.gather.app.utils;

import com.vessel.gather.app.constant.HttpType;
import com.vessel.gather.app.data.entity.CommonResponse;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by zhangwei on 2017/8/28.
 * 通用Http转换
 * 对于只关注请求成功与否没有Entity的情况, 若成功则走onNext，否则抛出{@link ServiceException}
 */
public class HttpResultVoidFunc implements Function<CommonResponse<Void>, Boolean> {

    @Override
    public Boolean apply(@NonNull CommonResponse<Void> httpResult) throws Exception {
        if (httpResult.getStatus() == HttpType.HTTP_OK) {
            return true;
        } else {
            throw new ServiceException(httpResult.getStatus(), httpResult.getMsg());
        }
    }
}