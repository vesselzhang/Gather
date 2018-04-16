package com.vessel.gather.widght;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public class TabEntity implements CustomTabEntity {

    private String mTabTitle;
    private int mTabSelectedIcon;
    private int mTabUnelectedIcon;

    public TabEntity(String tabTitle, int tabSelectedIcon, int tabUnelectedIcon) {
        mTabTitle = tabTitle;
        mTabSelectedIcon = tabSelectedIcon;
        mTabUnelectedIcon = tabUnelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return mTabTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return mTabSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return mTabUnelectedIcon;
    }
}
