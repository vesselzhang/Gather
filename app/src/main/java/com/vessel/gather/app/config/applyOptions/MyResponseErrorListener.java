package com.vessel.gather.app.config.applyOptions;

import android.content.Context;
import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.app.utils.ServiceException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import retrofit2.HttpException;

import static java.net.HttpURLConnection.HTTP_BAD_METHOD;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_SERVER_ERROR;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public class MyResponseErrorListener implements ResponseErrorListener {
    @Override
    public void handleResponseError(Context context, Throwable t) {
        /* 用来提供处理所有错误的监听
           rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效 */
        String msg = null;
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = "数据解析错误";
        } else if (t instanceof ServiceException) {
            msg = ((ServiceException) t).getMsg();
        }
        if (!TextUtils.isEmpty(msg))
            ArmsUtils.makeText(context, msg);
        else
            ArmsUtils.makeText(context, "未知错误：" + t.getMessage());
    }

    private String convertStatusCode(HttpException httpException) {
        if (httpException.code() == HTTP_SERVER_ERROR) {
            return "服务器发生错误";
        } else if (httpException.code() == HTTP_BAD_METHOD) {
            return "请求方法异常";
        } else if (httpException.code() == HTTP_NOT_FOUND) {
            return "请求地址不存在";
        } else if (httpException.code() == HTTP_FORBIDDEN) {
            return "请求被服务器拒绝";
        } else if (httpException.code() == HTTP_UNAUTHORIZED) {
            return "认证错误";
        } else {
            return httpException.message();
        }
    }
}