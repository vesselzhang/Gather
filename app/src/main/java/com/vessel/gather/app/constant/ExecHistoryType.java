package com.vessel.gather.app.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        ExecHistoryType.TYPE_VOICE, ExecHistoryType.TYPE_COUPON,
        ExecHistoryType.TYPE_CUSTOMER
})

/**
 * @author vesselzhang
 * @date 2017/12/11
 */

public @interface ExecHistoryType {
    int TYPE_VOICE = 0;
    int TYPE_COUPON = 1;
    int TYPE_CUSTOMER = 2;
}