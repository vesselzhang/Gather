package com.vessel.gather.module;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.di.component.AppComponent;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.module.bbs.BbsTabFragment;
import com.vessel.gather.module.cart.CartTabFragment;
import com.vessel.gather.module.home.HomeTabFragment;
import com.vessel.gather.module.me.MeTabFragment;
import com.vessel.gather.widght.TabEntity;

import java.util.ArrayList;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

@Route(path = "/app/main")
public class MainActivity extends MySupportActivity {

    @BindView(R.id.tl_bottom_bar)
    CommonTabLayout mBottomBar;

    private ISupportFragment[] mFragments = new ISupportFragment[4];

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.main_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initFragmentation();
        initBottomBar();
    }

    private void initFragmentation() {
        ISupportFragment homeFragment = findFragment(HomeTabFragment.class);
        if (homeFragment == null) {
            mFragments[0] = HomeTabFragment.newInstance();
            mFragments[1] = BbsTabFragment.newInstance();
            mFragments[2] = CartTabFragment.newInstance();
            mFragments[3] = MeTabFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_content, 0, mFragments);
        } else {
            mFragments[0] = findFragment(HomeTabFragment.class);
            mFragments[1] = findFragment(BbsTabFragment.class);
            mFragments[2] = findFragment(CartTabFragment.class);
            mFragments[3] = findFragment(MeTabFragment.class);
        }
    }

    private void initBottomBar() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        mTabEntities.add(new TabEntity("首页", R.drawable.bt_shouye_h, R.drawable.bt_shouye_n));
        mTabEntities.add(new TabEntity("论坛", R.drawable.bt_xiaoxi_h, R.drawable.bt_xiaoxi_n));
        mTabEntities.add(new TabEntity("购物车", R.drawable.bt_gouwu_h, R.drawable.bt_gouwu_n));
        mTabEntities.add(new TabEntity("我的", R.drawable.bt_wode_h, R.drawable.bt_wode_n));
        mBottomBar.setTabData(mTabEntities);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showHideFragment(mFragments[position]);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
}
