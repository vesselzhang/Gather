package com.vessel.gather.app.config.lifecyclesOptioins;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.delegate.AppLifecycles;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.tencent.smtt.sdk.QbSdk;
import com.vessel.gather.BuildConfig;

import me.yokeyword.fragmentation.Fragmentation;
import timber.log.Timber;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public class MyAppLifecycle implements AppLifecycles {

    @Override
    public void onCreate(Application application) {
        initTimber();
//        initLeakCanary(application);
//        initFragmentation();
        initARouter(application);
        RxPaparazzo.register(application);
        QbSdk.initX5Environment(application, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d("app", " onViewInitFinished is " + b);
            }
        });
    }

    @Override
    public void onTerminate(Application application) {

    }

    @Override
    public void attachBaseContext(Context base) {

    }

    private void initFragmentation() {
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出  默认NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.SHAKE)
                // 开发环境：true时，遇到异常："Can not perform this action after onSaveInstanceState!"时，抛出，并Crash;
                // 生产环境：false时，不抛出，不会Crash，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 生产环境时，捕获上述异常（避免crash），会捕获
                // 建议在回调处上传下面异常到崩溃监控服务器
                .handleException(e -> {
                    // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                    // Bugtags.sendException(e);
                })
                .install();
    }

    private void initTimber() {
        if (BuildConfig.LOG_DEBUG) {
            //Timber日志打印
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initARouter(Application application) {
        if (BuildConfig.LOG_DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
            ARouter.printStackTrace(); // 打印日志的时候打印线程堆栈
        }
        ARouter.init(application);
    }
}