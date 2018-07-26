package com.vessel.gather.module;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.module.cart.CartTabFragment;
import com.vessel.gather.module.home.SearchFragment;
import com.vessel.gather.module.home.SellerApplyFragment;
import com.vessel.gather.module.home.SellerListFragment;
import com.vessel.gather.module.home.WorkerApplyFragment;
import com.vessel.gather.module.home.WorkerDetailFragment;
import com.vessel.gather.module.home.WorkerListFragment;
import com.vessel.gather.module.me.AboutFragment;
import com.vessel.gather.module.me.AddressFragment;
import com.vessel.gather.module.me.CollectFragment;
import com.vessel.gather.module.me.MemoFragment;
import com.vessel.gather.module.me.OrderFragment;
import com.vessel.gather.module.me.SettingFragment;
import com.vessel.gather.module.me.SuggestFragment;

import me.yokeyword.fragmentation.ISupportFragment;

import static com.vessel.gather.app.constant.Constants.DEFAULT_LONG;
import static com.vessel.gather.app.constant.Constants.PAGE;
import static com.vessel.gather.app.constant.Constants.PAGE_ABOUT;
import static com.vessel.gather.app.constant.Constants.PAGE_ADDRESS;
import static com.vessel.gather.app.constant.Constants.PAGE_CART;
import static com.vessel.gather.app.constant.Constants.PAGE_COLLECT;
import static com.vessel.gather.app.constant.Constants.PAGE_MEMO;
import static com.vessel.gather.app.constant.Constants.PAGE_ORDER;
import static com.vessel.gather.app.constant.Constants.PAGE_SEARCH;
import static com.vessel.gather.app.constant.Constants.PAGE_SELLER;
import static com.vessel.gather.app.constant.Constants.PAGE_SELLER_APPLY;
import static com.vessel.gather.app.constant.Constants.PAGE_SELLER_LIST;
import static com.vessel.gather.app.constant.Constants.PAGE_SETTING;
import static com.vessel.gather.app.constant.Constants.PAGE_SUGGEST;
import static com.vessel.gather.app.constant.Constants.PAGE_WORKER;
import static com.vessel.gather.app.constant.Constants.PAGE_WORKER_APPLY;
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
            case PAGE_COLLECT:
                targetFragment = CollectFragment.newInstance();
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
            case PAGE_ABOUT:
                targetFragment = AboutFragment.newInstance();
                break;
            case PAGE_SELLER_APPLY:
                targetFragment = SellerApplyFragment.newInstance();
                break;
            case PAGE_WORKER_APPLY:
                targetFragment = WorkerApplyFragment.newInstance();
                break;
            case PAGE_SELLER_LIST:
                targetFragment = SellerListFragment.newInstance();
                break;
            case PAGE_WORKER_LIST:
                targetFragment = WorkerListFragment.newInstance();
                break;
            case PAGE_WORKER:
                long workerId = getIntent().getLongExtra(Constants.KEY_WORKER_ID, DEFAULT_LONG);
                if (workerId == DEFAULT_LONG) {
                    return;
                }
                targetFragment = WorkerDetailFragment.newInstance(true, workerId);
                break;
            case PAGE_SELLER:
//                targetFragment = WorkerDetailFragment.newInstance();
                break;
            case PAGE_SEARCH:
                targetFragment = SearchFragment.newInstance();
                break;
            case PAGE_CART:
                targetFragment = CartTabFragment.newInstance();
                break;
        }
        loadRootFragment(R.id.fl_content, targetFragment);
    }
}
