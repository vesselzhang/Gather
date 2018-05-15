package com.vessel.gather.module.home.di;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.data.entity.ServiceListResponse;
import com.vessel.gather.app.data.entity.ServiceListResponse.ArtisansBean;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.module.home.adapter.TypeAdapter;
import com.vessel.gather.module.home.adapter.WorkerListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class WorkerListPresenter extends BasePresenter<WorkerListContract.Model, WorkerListContract.View> implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    private WorkerListAdapter mAdapter;
    private TypeAdapter mPopAdapter;
    private List<ArtisansBean> mShopList;
    private List<TypeListResponse.TypesBean> mPopList = new ArrayList<>();
    private int index = 1;
    private int mTypeId = -1;

    @Inject
    public WorkerListPresenter(WorkerListContract.Model model, WorkerListContract.View rootView) {
        super(model, rootView);
        mShopList = new ArrayList<>();
    }

    public void getShopList(boolean pullToRefresh) {
        if (mAdapter == null) {
            mAdapter = new WorkerListAdapter(mAppManager.getCurrentActivity());
            mRootView.setAdapter(mAdapter);
        }
        if (pullToRefresh) index = 1;//上拉刷新默认只请求第一页
        Map<String, Object> map = new HashMap<>();
        map.put("type", 0);
        map.put("page", index);
        if (mTypeId != -1) {
            map.put("typeId", mTypeId);
        }
        map.put("pageSize", 100);
        mModel.queryServiceList(map)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<ServiceListResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ServiceListResponse serviceListResponse) {
                        index += 1;
                        if (pullToRefresh) {
                            if (serviceListResponse.getArtisans() == null) {
                                return;
                            }
                            mShopList.clear();
                            mShopList.addAll(serviceListResponse.getArtisans());
                            mAdapter.setList(serviceListResponse.getArtisans());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            if (serviceListResponse.getArtisans() == null) {
                                return;
                            }
                            mShopList.addAll(serviceListResponse.getArtisans());
                            mAdapter.addList(serviceListResponse.getArtisans());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void queryTypeList() {
        mModel.queryTypeList(0)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<TypeListResponse>(mErrorHandler) {
                    @Override
                    public void onNext(TypeListResponse typeListResponse) {
                        TypeListResponse.TypesBean temp = new TypeListResponse.TypesBean();
                        temp.setTypeId(-1);
                        temp.setTypeName("全部");
                        mPopList.add(temp);
                        mPopList.addAll(typeListResponse.getTypes());
                        mTypeId = -1;
                        mRootView.updatePop(mPopList, 0);
                    }
                });
    }

    public void showPop(View view) {
        if (mPopAdapter == null) {
            mPopAdapter = new TypeAdapter(mAppManager.getCurrentActivity());
        }

        View contentView = LayoutInflater.from(mAppManager.getCurrentActivity()).inflate(R.layout.home_layout_seller_type, null);
        ListView mLv = contentView.findViewById(R.id.pop_shop_lv);
        mLv.setAdapter(mPopAdapter);
        mPopAdapter.setList(mPopList);
        //自适应大小
        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        //在控件上方显示
        popupWindow.showAsDropDown(view, 0, 0);
//        popupWindow.showAtLocation(view, Gravity.RIGHT, 0, location[1] + view.getHeight());
        mLv.setOnItemClickListener((adapterView, view1, i, l) -> {
            mTypeId = mPopList.get(i).getTypeId();
            mRootView.updatePop(mPopList, i);
            popupWindow.dismiss();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ArtisansBean artisansBean = (ArtisansBean) adapterView.getAdapter().getItem(i);
//        ARouter.getInstance().build("/app/web")
//                .withSerializable(Constants.WEB_TYPE, 0)
//                .withSerializable(Constants.WEB_ID, artisansBean.getArtisanId())
//                .navigation();
        mRootView.jumpWorkerPage(artisansBean);
    }

    @Override
    public void onRefresh() {
        getShopList(true);
    }
}