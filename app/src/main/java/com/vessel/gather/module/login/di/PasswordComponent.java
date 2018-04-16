package com.vessel.gather.module.login.di;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.vessel.gather.module.login.PasswordActivity;

import dagger.Component;

/**
 * @author vesselzhang
 * @date 2017/12/18
 */

@ActivityScope
@Component(modules = PasswordModule.class, dependencies = AppComponent.class)
public interface PasswordComponent {
    void inject(PasswordActivity activity);
}