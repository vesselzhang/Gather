package com.vessel.gather.app.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        HttpType.HTTP_OK, HttpType.HTTP_FAILED,
        HttpType.HTTP_UNAUTHORIZED, HttpType.HTTP_SERVICE_ERROR
})

/**
 * @author vesselzhang
 * @date 2017/12/11
 */

public @interface HttpType {
    int HTTP_OK = 0;
    int HTTP_FAILED = 1;
    int HTTP_UNAUTHORIZED = -2;
    int HTTP_SERVICE_ERROR = -1;
}