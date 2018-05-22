package com.vessel.gather.module;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vessel.gather.R;
import com.vessel.gather.app.base.BackService;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.module.cart.CartTabFragment;
import com.vessel.gather.module.di.DaggerMainComponent;
import com.vessel.gather.module.di.MainContract;
import com.vessel.gather.module.di.MainModule;
import com.vessel.gather.module.di.MainPresenter;
import com.vessel.gather.module.home.HomeTabFragment;
import com.vessel.gather.module.me.MeTabFragment;
import com.vessel.gather.widght.TabEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.ISupportFragment;

@Route(path = "/app/main")
public class MainActivity extends MySupportActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tl_bottom_bar)
    CommonTabLayout mBottomBar;

    private long time = 0;
    private ISupportFragment[] mFragments = new ISupportFragment[3];

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.main_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtil.requestPermission(
                    new PermissionUtil.RequestPermission() {
                        @Override
                        public void onRequestPermissionSuccess() {

                        }

                        @Override
                        public void onRequestPermissionFailure(List<String> list) {

                        }

                        @Override
                        public void onRequestPermissionFailureWithAskNeverAgain(List<String> list) {

                        }
                    },
                    new RxPermissions(this), ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE);
        }
        startService(new Intent(this, BackService.class));
        initFragmentation();
        initBottomBar();
        checkVersion();

        mPresenter.getIndexInfo();
    }

    private void initFragmentation() {
        ISupportFragment homeFragment = findFragment(HomeTabFragment.class);
        if (homeFragment == null) {
            mFragments[0] = HomeTabFragment.newInstance();
            mFragments[1] = CartTabFragment.newInstance();
            mFragments[2] = MeTabFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_content, 0, mFragments);
        } else {
            mFragments[0] = findFragment(HomeTabFragment.class);
            mFragments[1] = findFragment(CartTabFragment.class);
            mFragments[2] = findFragment(MeTabFragment.class);
        }
    }

    private void initBottomBar() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        mTabEntities.add(new TabEntity("首页", R.drawable.bt_shouye_h, R.drawable.bt_shouye_n));
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

    private void checkVersion() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(integer -> {
                    try {
                        Date date = new Date(getSharedPreferences("config", 0).getLong(SPConstant.SP_VERSION_CHECK_TIME, 0));//获取取消时间点的第二天

                        Calendar tempCalendarNext = Calendar.getInstance();
                        tempCalendarNext.setTime(date);
                        tempCalendarNext.add(Calendar.DATE, 1);

                        if (tempCalendarNext.getTime().before(new Date())) {
                            mPresenter.checkVersion();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 1000) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void killMyself() {

    }
}
