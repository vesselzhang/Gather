package com.vessel.gather.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.module.home.di.SearchContract;
import com.vessel.gather.module.home.di.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

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

    private List<View> views = new ArrayList<>();

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_search, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        views.clear();
        views.add(bottom_cailiao);
        views.add(bottom_dianpu);
        views.add(bottom_jineng);
        views.add(bottom_jigong);
        updateView(R.id.search_cailiao_bottom);
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
        switch (view.getId()) {
            case R.id.search_btn:
                historyLayout.setVisibility(View.GONE);
                break;
            case R.id.search_cailiao:
                updateView(R.id.search_cailiao_bottom);
                break;
            case R.id.search_dianpu:
                updateView(R.id.search_dianpu_bottom);
                break;
            case R.id.search_jineng:
                updateView(R.id.search_jineng_bottom);
                break;
            case R.id.search_jigong:
                updateView(R.id.search_jigong_bottom);
                break;
        }
    }

    private void updateView(int viewId) {
        for (View view : views) {
            if (view.getId() == viewId) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }
}
