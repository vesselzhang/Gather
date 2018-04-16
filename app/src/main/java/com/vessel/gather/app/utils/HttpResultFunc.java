package com.vessel.gather.app.utils;

import com.vessel.gather.app.constant.HttpType;
import com.vessel.gather.app.data.entity.CommonResponse;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by vesselzhang on 2017/8/24.
 * 通用Http转换
 * 用来统一处理Http的resultCode, 并将{@link CommonResponse}的T部分剥离出来返回给subscriber
 */
public class HttpResultFunc<T> implements Function<CommonResponse<T>, T> {

    @Override
    public T apply(@NonNull CommonResponse<T> httpResult) throws Exception {
        if (httpResult.getStatus() == HttpType.HTTP_OK) {
            return httpResult.getData();
        } else {
            throw new ServiceException(httpResult.getStatus(), httpResult.getMsg());
        }
    }
}