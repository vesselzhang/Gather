package com.vessel.gather.module.home.di;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.app.constant.SearchType;
import com.vessel.gather.app.data.entity.SearchResponse;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;
import com.vessel.gather.module.home.adapter.SearchAdapter;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class SearchPresenter extends BasePresenter<SearchContract.Model, SearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    SearchAdapter mAdatper;
    @Inject
    List<MultiItemEntity> mList;

    @Inject
    public SearchPresenter(SearchContract.Model model, SearchContract.View rootView) {
        super(model, rootView);
    }

    public void searchInfo(Map<String, Object> map, @SearchType int searchType) {
        mList.clear();
        mAdatper.notifyDataSetChanged();
        mModel.searchInfo(map)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ProgressSubscriber<SearchResponse>(mAppManager.getCurrentActivity(), mErrorHandler) {
                               @Override
                               public void onNext(SearchResponse searchResponse) {
                                   super.onNext(searchResponse);
                                   mList.clear();
                                   switch (searchType) {
                                       case SearchType.TYPE_PRODUCT:
                                           if (searchResponse.getProducts() != null && searchResponse.getProducts().size() > 0) {
                                               mList.addAll(searchResponse.getProducts());
                                           }
                                           break;
                                       case SearchType.TYPE_ARTISAN:
                                           if (searchResponse.getArtisans() != null && searchResponse.getArtisans().size() > 0) {
                                               mList.addAll(searchResponse.getArtisans());
                                           }
                                           break;
                                       case SearchType.TYPE_SHOP:
                                           if (searchResponse.getShops() != null && searchResponse.getShops().size() > 0) {
                                               mList.addAll(searchResponse.getShops());
                                           }
                                           break;
                                       case SearchType.TYPE_SKILL:
                                           if (searchResponse.getSkills() != null && searchResponse.getSkills().size() > 0) {
                                               mList.addAll(searchResponse.getSkills());
                                           }
                                           break;
                                   }
                                   mAdatper.notifyDataSetChanged();
                               }
                           }
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}