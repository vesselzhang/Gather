package com.vessel.gather.app.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        WebType.WEB_URL, WebType.WEB_SHOP,
        WebType.WEB_SKILL, WebType.WEB_OTHER
})

/**
 * @author vesselzhang
 * @date 2018/07/26
 */

public @interface WebType {
    int WEB_URL = 0;
    int WEB_SHOP = 1;
    int WEB_SKILL = 2;
    int WEB_OTHER = 99;
}