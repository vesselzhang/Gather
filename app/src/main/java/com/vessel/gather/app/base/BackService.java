package com.vessel.gather.app.base;

import com.jess.arms.base.App;
import com.jess.arms.base.BaseService;
import com.jess.arms.integration.IRepositoryManager;

/**
 * @author vesselzhang
 * @date 2017/12/13
 */

public class BackService extends BaseService {

    private IRepositoryManager mRepositoryManager;

    @Override
    public void init() {
        mRepositoryManager = ((App) getApplication()).getAppComponent().repositoryManager();
    }
}
