package com.vessel.gather.app.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseService;
import com.jess.arms.integration.IRepositoryManager;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.app.utils.FileDownloadThread;
import com.vessel.gather.event.Event;

import org.simple.eventbus.Subscriber;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static com.vessel.gather.event.Event.EVENT_DOWNLOAD_APK;

/**
 * @author vesselzhang
 * @date 2017/12/13
 */

public class BackService extends BaseService {

    private IRepositoryManager mRepositoryManager;

    @Override
    public void init() {
        mRepositoryManager = ((App) getApplication()).getAppComponent().repositoryManager();
    }

    @Subscriber(tag = EVENT_DOWNLOAD_APK)
    public void updateEvent(Event event) {
        doDownload(event.getDownloadUrl());
    }

    private void doDownload(String dowloadUrl) {
        String path = Constants.SD_APK_DIR + "/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = "Juji.apk";
        String filepath = path + fileName;

        DownloadTask task = new DownloadTask(dowloadUrl, 3, filepath);
        task.start();
    }

    private class DownloadTask extends Thread {
        private String downloadUrl;// 下载链接地址
        private int threadNum;// 开启的线程数
        private String filePath;// 保存文件路径地址
        private int blockSize;// 每一个线程的下载量

        public DownloadTask(String downloadUrl, int threadNum, String fileptah) {
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
                installNormal(getApplicationContext(), filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setPermission(String filePath) {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void installNormal(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.vessel.gather.file_provider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        }
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
