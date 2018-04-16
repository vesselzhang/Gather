package com.vessel.gather.app.utils.progress;

import android.app.ProgressDialog;
import android.content.Context;

import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author vesselzhang
 * @date 2018/4/14
 */
public abstract class ProgressSubscriber<T> extends ErrorHandleSubscriber<T> {
    private ProgressDialog progressDialog;
    private Context context;
    private Disposable disposable;

    public ProgressSubscriber(Context context, RxErrorHandler rxErrorHandler) {
        super(rxErrorHandler);
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setOnCancelListener(dialogInterface -> {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        });
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        this.disposable = d;
        if (context != null && progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        if (context != null && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (context != null && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onError(e);
    }
}
