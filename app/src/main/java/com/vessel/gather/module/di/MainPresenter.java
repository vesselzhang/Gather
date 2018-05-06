package com.vessel.gather.module.di;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.app.data.entity.CheckVersionResponse;
import com.vessel.gather.app.data.entity.IndexResponse;
import com.vessel.gather.app.utils.FileDownloadThread;
import com.vessel.gather.widght.CustomDialog;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void getIndexInfo() {
        mModel.getIndexInfo()
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<IndexResponse>(mErrorHandler) {
                    @Override
                    public void onNext(IndexResponse indexResponse) {
                        EventBus.getDefault().post(indexResponse, Constants.TAG_BANNERS_REFRESH);
                    }
                });
    }

    public void checkVersion() {
        mModel.checkVersion().subscribe(
                new Observer<CheckVersionResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(CheckVersionResponse checkVersionResponse) {
                        Context activity = mAppManager.getCurrentActivity();
                        try {
                            PackageManager manager = activity.getPackageManager();
                            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
                            if (info.versionCode < checkVersionResponse.getVersionCode()) {
                                if (checkVersionResponse.getMust() == 1) {
                                    CustomDialog selfDialog = new CustomDialog(activity);
                                    selfDialog.setTitle("系统提示");
                                    selfDialog.setMessage("您的版本已经过于老旧, 为了更好的体验请更新最新版本。");
                                    selfDialog.setYesOnclickListener("确定", () -> {
                                        doDownload(checkVersionResponse.getDownloadUrl());
                                        mRootView.showMessage("版本下载中, 请稍候...");
                                        selfDialog.dismiss();
                                    });
                                    selfDialog.setNoOnclickListener("取消", () -> {
                                        SharedPreferences.Editor editor = activity.getSharedPreferences("config", 0).edit();
                                        editor.putLong("updateTime", System.currentTimeMillis());
                                        editor.commit();
                                        selfDialog.dismiss();
                                    });
                                    selfDialog.show();
                                } else {
                                    CustomDialog selfDialog = new CustomDialog(activity);
                                    selfDialog.setTitle("系统提示");
                                    selfDialog.setMessage("检查到新版本" + checkVersionResponse.getVersionName() + "，立即更新？");
                                    selfDialog.setYesOnclickListener("确定", () -> {
                                        doDownload(checkVersionResponse.getDownloadUrl());
                                        mRootView.showMessage("版本下载中, 请稍候...");
                                        selfDialog.dismiss();
                                    });
                                    selfDialog.setNoOnclickListener("取消", () -> {
                                        SharedPreferences.Editor editor = activity.getSharedPreferences("config", 0).edit();
                                        editor.putLong(SPConstant.SP_VERSION_CHECK_TIME, System.currentTimeMillis());
                                        editor.commit();
                                        selfDialog.dismiss();
                                    });
                                    selfDialog.show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }
        );
    }

    private void doDownload(String dowloadUrl) {
        String path = Constants.SD_APK_DIR + "/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        String fileName = "Juji.apk";
        String filepath = path + fileName;

        downloadTask task = new downloadTask(dowloadUrl, 3, filepath);
        task.start();
    }

    private class downloadTask extends Thread {
        private String downloadUrl;// 下载链接地址
        private int threadNum;// 开启的线程数
        private String filePath;// 保存文件路径地址
        private int blockSize;// 每一个线程的下载量

        public downloadTask(String downloadUrl, int threadNum, String fileptah) {
            this.downloadUrl = downloadUrl;
            this.threadNum = threadNum;
            this.filePath = fileptah;
        }

        @Override
        public void run() {

            FileDownloadThread[] threads = new FileDownloadThread[threadNum];
            try {
                URL url = new URL(downloadUrl);
                Log.d(TAG, "download file http path:" + downloadUrl);
                URLConnection conn = url.openConnection();
                // 读取下载文件总大小
                int fileSize = conn.getContentLength();
                if (fileSize <= 0) {
                    Log.e(TAG, "读取文件失败");
                    return;
                }

                // 计算每条线程下载的数据长度
                blockSize = (fileSize % threadNum) == 0 ? fileSize / threadNum
                        : fileSize / threadNum + 1;

                Log.d(TAG, "fileSize:" + fileSize + "  blockSize:" + blockSize);

                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                for (int i = 0; i < threads.length; i++) {
                    // 启动线程，分别下载每个线程需要下载的部分
                    threads[i] = new FileDownloadThread(url, file, blockSize,
                            (i + 1));
                    threads[i].setName("Thread:" + i);
                    threads[i].start();
                }

                boolean isfinished = false;
                int downloadedAllSize = 0;
                while (!isfinished) {
                    isfinished = true;
                    // 当前所有线程下载总量
                    downloadedAllSize = 0;
                    for (int i = 0; i < threads.length; i++) {
                        downloadedAllSize += threads[i].getDownloadLength();
                        if (!threads[i].isCompleted()) {
                            isfinished = false;
                        }
                    }
                    // 通知handler去更新视图组件
                    Message msg = new Message();
                    msg.getData().putInt("size", (downloadedAllSize * 100) / fileSize);
//                    mHandler.sendMessage(msg);
                    Thread.sleep(1000);// 休息1秒后再读取下载进度
                }
                Log.d(TAG, "all of downloadSize:" + downloadedAllSize);

                setPermission(filePath);
                installNormal(mAppManager.getCurrentActivity(), filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setPermission(String filePath)  {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void installNormal(Context context,String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.vessel.gather.file_provider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}