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
    public RxCache configRxCache(Context context, RxCache.Builder builder) {
        builder.useExpiredDataIfLoaderNotAvailable(true);
        // 想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 fastjson, 请 return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());
        // 否则请 return null;
        return null;
    }
}