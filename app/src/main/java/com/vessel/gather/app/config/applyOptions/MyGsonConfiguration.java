package com.vessel.gather.app.config.applyOptions;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.jess.arms.di.module.AppModule;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public class MyGsonConfiguration implements AppModule.GsonConfiguration {
    @Override
    public void configGson(Context context, GsonBuilder builder) {
        builder
                .serializeNulls()//支持序列化null的参数
                .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
    }
}