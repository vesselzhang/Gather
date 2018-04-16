package com.vessel.gather.app.config.applyOptions;

import android.content.Context;

import com.jess.arms.di.module.ClientModule;

import io.rx_cache2.internal.RxCache;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public class MyRxCacheConfiguration implements ClientModule.RxCacheConfiguration {
    @Override
    public void configRxCache(Context context, RxCache.Builder builder) {
        // 当数据无法加载时，使用过期数据
        builder.useExpiredDataIfLoaderNotAvailable(true);
    }
}