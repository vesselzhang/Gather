package com.vessel.gather.module;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportActivity;
import com.vessel.gather.module.me.MeSettingFragment;

import me.yokeyword.fragmentation.ISupportFragment;

import static com.vessel.gather.app.constant.Constants.PAGE;
import static com.vessel.gather.app.constant.Constants.PAGE_SETTING;

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
                targetFragment = MeSettingFragment.newInstance();
                break;
        }
        loadRootFragment(R.id.fl_content, targetFragment);
    }
}
