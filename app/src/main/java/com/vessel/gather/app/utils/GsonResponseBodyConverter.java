package com.vessel.gather.app.utils;

import com.google.gson.Gson;
import com.vessel.gather.app.constant.HttpType;
import com.vessel.gather.app.data.entity.CommonResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type adapter) {
        this.gson = gson;
        this.type = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        CommonResponse commonResponse = gson.fromJson(json, CommonResponse.class);
        if (HttpType.HTTP_OK == commonResponse.getStatus()) {
            return gson.fromJson(json, type);
        } else {
            throw new ServiceException(commonResponse.getStatus(), commonResponse.getMsg());
        }
    }
}
