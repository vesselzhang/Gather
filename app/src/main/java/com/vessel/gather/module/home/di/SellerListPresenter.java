package com.vessel.gather.module.home.di;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.data.entity.ServiceListResponse;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.module.home.adapter.SellerListAdapter;
import com.vessel.gather.module.home.adapter.SellerTypeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class SellerListPresenter extends BasePresenter<SellerListContract.Model, SellerListContract.View> implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    private SellerListAdapter mAdapter;
    private SellerTypeAdapter mPopAdapter;
    private List<ServiceListResponse.ShopsBean> mShopList;
    private List<TypeListResponse.TypesBean> mPopList = new ArrayList<>();
    private int index = 1;
    private int mTypeId = -1;

    private PopupWindow popupWindow;

    @Inject
    public SellerListPresenter(SellerListContract.Model model, SellerListContract.View rootView) {
        super(model, rootView);
        mShopList = new ArrayList<>();
    }

    public void getShopList(boolean pullToRefresh) {
        if (mAdapter == null) {
            mAdapter = new SellerListAdapter(mAppManager.getCurrentActivity());
            mRootView.setAdapter(mAdapter);
        }
        if (pullToRefresh) index = 1;//上拉刷新默认只请求第一页
        Map<String, Object> map = new HashMap<>();
        map.put("type", 1);
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
                            mShopList.clear();
                            mShopList.addAll(serviceListResponse.getShops());
                            mAdapter.setList(serviceListResponse.getShops());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mShopList.addAll(serviceListResponse.getShops());
                            mAdapter.addList(serviceListResponse.getShops());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void queryTypeList() {
        mModel.queryTypeList(1)
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
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
            return;
        }
        if (mPopAdapter == null) {
            mPopAdapter = new SellerTypeAdapter(mAppManager.getCurrentActivity());
        }

        View contentView = LayoutInflater.from(mAppManager.getCurrentActivity()).inflate(R.layout.home_layout_seller_type, null);
        ListView mLv = contentView.findViewById(R.id.pop_shop_lv);
        mLv.setAdapter(mPopAdapter);
        mPopAdapter.setList(mPopList);
        //自适应大小
        popupWindow = new PopupWindow();
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        //点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        //在控件上方显示
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, location[1] + view.getHeight());
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
        ARouter.getInstance().build("/app/web").navigation();
    }

    @Override
    public void onRefresh() {
        getShopList(true);
    }
}