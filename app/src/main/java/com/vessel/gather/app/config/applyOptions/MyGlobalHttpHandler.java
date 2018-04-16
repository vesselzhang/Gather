package com.vessel.gather.app.config.applyOptions;

import android.content.Context;
import android.text.TextUtils;

import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.utils.DataHelper;
import com.vessel.gather.app.constant.Constants;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public class MyGlobalHttpHandler implements GlobalHttpHandler {

    private Context mContext;

    public MyGlobalHttpHandler(Context context) {
        this.mContext = context;
    }

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        // 统一处理http响应。eg:状态码不是200时，根据状态码做相应的处理。
        return response;
    }

    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        // 统一处理http请求。eg:给request统一添加token或者header以及参数加密等操作
        String token = DataHelper.getStringSF(mContext, Constants.KEY_APP_TOKEN);
        if (TextUtils.isEmpty(token)) {
            return request;
        } else {
            return chain.request().newBuilder().header("Authorization", token).build();
        }
    }
}