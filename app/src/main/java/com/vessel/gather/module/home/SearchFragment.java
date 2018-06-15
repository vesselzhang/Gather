package com.vessel.gather.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.constant.SearchType;
import com.vessel.gather.module.home.adapter.SearchAdapter;
import com.vessel.gather.module.home.di.DaggerSearchComponent;
import com.vessel.gather.module.home.di.SearchContract;
import com.vessel.gather.module.home.di.SearchModule;
import com.vessel.gather.module.home.di.SearchPresenter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/29
 */

public class SearchFragment extends MySupportFragment<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.search_edit)
    EditText edit;

    @BindView(R.id.search_cailiao_bottom)
    View bottom_cailiao;
    @BindView(R.id.search_dianpu_bottom)
    View bottom_dianpu;
    @BindView(R.id.search_jineng_bottom)
    View bottom_jineng;
    @BindView(R.id.search_jigong_bottom)
    View bottom_jigong;

    @BindView(R.id.search_history_tips)
    View historyLayout;
    @BindView(R.id.search_result)
    RecyclerView result;

    @Inject
    SearchAdapter mAdatper;

    private @SearchType
    int searchType = SearchType.TYPE_PRODUCT;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerSearchComponent
                .builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_search, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initRecycler();
        updateView();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String s) {
        ArmsUtils.makeText(getActivity(), s);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @OnClick({R.id.search_btn, R.id.search_cailiao, R.id.search_dianpu, R.id.search_jineng, R.id.search_jigong})
    public void onClick(View view) {
        Map<String, Object> map = new HashMap<>();
        switch (view.getId()) {
            case R.id.search_btn:
                historyLayout.setVisibility(View.GONE);
                map.put("type", searchType);
                map.put("keyword", edit.getText().toString());
                map.put("page", 1);
                map.put("pageSize", 100);
                mPresenter.searchInfo(map, searchType);
                break;
            case R.id.search_cailiao:
                searchType = SearchType.TYPE_PRODUCT;
                updateView();
                if (!TextUtils.isEmpty(edit.getText().toString())) {
                    map.put("type", searchType);
                    map.put("keyword", edit.getText().toString());
                    map.put("page", 1);
                    map.put("pageSize", 100);
                    mPresenter.searchInfo(map, searchType);
                }
                break;
            case R.id.search_dianpu:
                searchType = SearchType.TYPE_SHOP;
                updateView();
                if (!TextUtils.isEmpty(edit.getText().toString())) {
                    map.put("type", searchType);
                    map.put("keyword", edit.getText().toString());
                    map.put("page", 1);
                    map.put("pageSize", 100);
                    mPresenter.searchInfo(map, searchType);
                }
                break;
            case R.id.search_jineng:
                searchType = SearchType.TYPE_SKILL;
                updateView();
                if (!TextUtils.isEmpty(edit.getText().toString())) {
                    map.put("type", searchType);
                    map.put("keyword", edit.getText().toString());
                    map.put("page", 1);
                    map.put("pageSize", 100);
                    mPresenter.searchInfo(map, searchType);
                }
                break;
            case R.id.search_jigong:
                searchType = SearchType.TYPE_ARTISAN;
                updateView();
                if (!TextUtils.isEmpty(edit.getText().toString())) {
                    map.put("type", searchType);
                    map.put("keyword", edit.getText().toString());
                    map.put("page", 1);
                    map.put("pageSize", 100);
                    mPresenter.searchInfo(map, searchType);
                }
                break;
        }
    }

    private void initRecycler() {
        result.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdatper.setOnItemClickListener(mPresenter);
        mAdatper.bindToRecyclerView(result);
        mAdatper.setEmptyView(R.layout.home_fragment_search_null);
    }

    private void updateView() {
        bottom_cailiao.setVisibility(View.INVISIBLE);
        bottom_dianpu.setVisibility(View.INVISIBLE);
        bottom_jineng.setVisibility(View.INVISIBLE);
        bottom_jigong.setVisibility(View.INVISIBLE);
        switch (searchType) {
            case SearchType.TYPE_ARTISAN:
                bottom_jigong.setVisibility(View.VISIBLE);
                break;
            case SearchType.TYPE_SHOP:
                bottom_dianpu.setVisibility(View.VISIBLE);
                break;
            case SearchType.TYPE_SKILL:
                bottom_jineng.setVisibility(View.VISIBLE);
                break;
            case SearchType.TYPE_PRODUCT:
                bottom_cailiao.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void jump2Worker(long artisanId) {
        start(WorkerDetailFragment.newInstance(false, artisanId));
    }
}
