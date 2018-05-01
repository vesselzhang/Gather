package com.vessel.gather.module;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.module.home.SellerListFragment;
import com.vessel.gather.module.me.AddressFragment;
import com.vessel.gather.module.me.MemoFragment;
import com.vessel.gather.module.me.OrderFragment;
import com.vessel.gather.module.me.SettingFragment;
import com.vessel.gather.module.me.SuggestFragment;

import me.yokeyword.fragmentation.ISupportFragment;

import static com.vessel.gather.app.constant.Constants.PAGE;
import static com.vessel.gather.app.constant.Constants.PAGE_ADDRESS;
import static com.vessel.gather.app.constant.Constants.PAGE_MEMO;
import static com.vessel.gather.app.constant.Constants.PAGE_ORDER;
import static com.vessel.gather.app.constant.Constants.PAGE_SELLER_LIST;
import static com.vessel.gather.app.constant.Constants.PAGE_SETTING;
import static com.vessel.gather.app.constant.Constants.PAGE_SUGGEST;
import static com.vessel.gather.app.constant.Constants.PAGE_WORKER_LIST;

/**
 * @author vesselzhang
 * @date 2017/11/28
 */
@Route(path = "/app/container")
public class ContainerActivity extends MySupportActivity {

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.container_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ISupportFragment targetFragment = null;
        String pageIndex = getIntent().getStringExtra(PAGE);
        if (TextUtils.isEmpty(pageIndex)) {
            ArmsUtils.snackbarText("错误参数");
            return;
        }
        switch (pageIndex) {
            case PAGE_SETTING:
                targetFragment = SettingFragment.newInstance();
                break;
            case PAGE_ORDER:
                targetFragment = OrderFragment.newInstance();
                break;
            case PAGE_ADDRESS:
                targetFragment = AddressFragment.newInstance();
                break;
            case PAGE_SUGGEST:
                targetFragment = SuggestFragment.newInstance();
                break;
            case PAGE_MEMO:
                targetFragment = MemoFragment.newInstance();
                break;
            case PAGE_SELLER_LIST:
                targetFragment = SellerListFragment.newInstance();
                break;
            case PAGE_WORKER_LIST:
                targetFragment = MemoFragment.newInstance();
                break;
        }
        loadRootFragment(R.id.fl_content, targetFragment);
    }
}
