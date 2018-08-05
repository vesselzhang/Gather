package com.vessel.gather.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.DataHelper;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.app.config.applyOptions.MyGlobalHttpHandler;
import com.vessel.gather.app.config.applyOptions.MyGsonConfiguration;
import com.vessel.gather.app.config.applyOptions.MyResponseErrorListener;
import com.vessel.gather.app.config.applyOptions.MyRetrofitConfiguration;
import com.vessel.gather.app.config.applyOptions.MyRxCacheConfiguration;
import com.vessel.gather.app.config.lifecyclesOptioins.MyActivityLifecycle;
import com.vessel.gather.app.config.lifecyclesOptioins.MyAppLifecycle;

import java.io.File;
import java.util.List;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public class GlobalConfiguration implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        //使用builder可以为框架配置一些配置信息
        builder
                .baseurl(BuildConfig.APP_DOMAIN)
                .retrofitConfiguration(new MyRetrofitConfiguration())
                .rxCacheConfiguration(new MyRxCacheConfiguration())
                .globalHttpHandler(new MyGlobalHttpHandler(context))
                .responseErrorListener(new MyResponseErrorListener())
                .cacheFile(new File(DataHelper.getCacheFile(context), "rxCache"))
                .gsonConfiguration(new MyGsonConfiguration());
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        //向Application的生命周期中注入一些自定义逻辑
        lifecycles.add(new MyAppLifecycle());
    }


    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //向Activity的生命周期中注入一些自定义逻辑
        lifecycles.add(new MyActivityLifecycle());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        //向Fragment的生命周期中注入一些自定义逻辑
    }
}