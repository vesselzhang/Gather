package com.vessel.gather.module;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.app.utils.X5WebView;

import butterknife.BindView;

import static com.vessel.gather.app.constant.Constants.WEB_ID;
import static com.vessel.gather.app.constant.Constants.WEB_SITE;
import static com.vessel.gather.app.constant.Constants.WEB_TYPE;

/**
 * @author vesselzhang
 * @date 2017/11/28
 */
@Route(path = "/app/web")
public class WebActivity extends MySupportActivity {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.webview)
    X5WebView mWebView;

    private String webUrl;
    private long id;
    private int type;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.web_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        webUrl = getIntent().getStringExtra(WEB_SITE);
        id = getIntent().getLongExtra(WEB_ID, -1);
        type = getIntent().getIntExtra(WEB_TYPE, -1);
        if (id == -1 || type == -1 || TextUtils.isEmpty(webUrl)) {
            ArmsUtils.makeText(this, "错误的请求地址");
            return;
        }

        String token = DataHelper.getStringSF(this, SPConstant.SP_TOKEN);
        if (TextUtils.isEmpty(token)) {
            if (id != -1 && type != -1) {
                webUrl = "http://120.78.63.213/assemble/resources/product.html?type=" + type + "&id=" + id;
            }
        } else {
            if (id != -1 && type != -1) {
                webUrl = "http://120.78.63.213/assemble/resources/product.html?token=" + token + "&type=" + type + "&id=" + id;
            }
        }

        progressBar.setMax(100);
        progressBar.setProgressDrawable(this.getResources().getDrawable(R.drawable.color_progressbar));

        initWebView();
    }

    private void initWebView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                progressBar.setProgress(i);
            }
        });

        mWebView.setDownloadListener((arg0, arg1, arg2, arg3, arg4) -> {
            TbsLog.d(TAG, "url: " + arg0);
            new AlertDialog.Builder(WebActivity.this)
                    .setTitle("allow to download？")
                    .setPositiveButton("yes", (dialog, which) -> {
                        Toast.makeText(WebActivity.this, "fake message: i'll download...", Toast.LENGTH_SHORT).show();
                    }).setNegativeButton("no", (dialog, which) -> {
                Toast.makeText(WebActivity.this, "fake message: refuse download...", Toast.LENGTH_SHORT).show();
            }).setOnCancelListener(dialog -> {
                Toast.makeText(WebActivity.this, "fake message: refuse download...", Toast.LENGTH_SHORT).show();
            }).show();
        });

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());

        long time = System.currentTimeMillis();
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        IX5WebViewExtension ix5 = mWebView.getX5WebViewExtension();
        if (ix5 != null) {
            ix5.setScrollBarFadingEnabled(false);
        }
        mWebView.loadUrl(webUrl);
        TbsLog.d("time-cost", "cost time: " + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            finish();
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
