package com.vessel.gather.app.config.applyOptions.intercept;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加UA拦截器，API需要加上UA才能正常使用
 *
 * @author vesselzhang
 * @date 2017/11/25
 */

public class UserAgentInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader("User-Agent")
//                .addHeader("User-Agent", Api.COMMON_UA_STR)
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}