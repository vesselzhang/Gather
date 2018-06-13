package com.vessel.gather.app.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        SearchType.TYPE_PRODUCT, SearchType.TYPE_ARTISAN,
        SearchType.TYPE_SHOP, SearchType.TYPE_SKILL
})

/**
 * @author vesselzhang
 * @date 2017/12/11
 */

public @interface SearchType {
    int TYPE_PRODUCT = 4;
    int TYPE_ARTISAN = 0;
    int TYPE_SHOP = 1;
    int TYPE_SKILL = 3;
}