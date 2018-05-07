package com.vessel.gather.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.module.me.adapter.ChildItem;
import com.vessel.gather.module.me.adapter.ExpandableListAdapter;
import com.vessel.gather.module.me.di.CollectContract;
import com.vessel.gather.module.me.di.CollectPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author vesselzhang
 * @date 2017/12/4
 */

public class CollectFragment extends MySupportFragment<CollectPresenter> implements CollectContract.View {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.tv_right)
    TextView mRightTV;

    @BindView(R.id.collect_expand_list)
    ExpandableListView expandableListView;

    private List<String> groupData;//group的数据源
    private Map<Integer, List<ChildItem>> childData;//child的数据源
    private ExpandableListAdapter mAdapter;

    public static CollectFragment newInstance() {
        Bundle args = new Bundle();

        CollectFragment fragment = new CollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_collect, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("我的收藏");
        mRightTV.setVisibility(View.VISIBLE);
        mRightTV.setText("编辑");

        setData(null);
        expandableListView.setAdapter(mAdapter);
        for (int i = 0; i < groupData.size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    @Override
    public void setData(Object data) {
        groupData = new ArrayList<>();
        groupData.add("技工师傅");
        groupData.add("技能");
        groupData.add("商品");
        groupData.add("店铺");

        List<ChildItem> childItems = new ArrayList<>();
        ChildItem childData1 = new ChildItem("苹果");
        childItems.add(childData1);
        ChildItem childData2 = new ChildItem("樱桃");
        childItems.add(childData2);
        ChildItem childData3 = new ChildItem("草莓");
        childItems.add(childData3);

        List<ChildItem> childItems2 = new ArrayList<>();
        ChildItem childData4 = new ChildItem("香蕉");
        childItems2.add(childData4);
        ChildItem childData5 = new ChildItem("芒果");
        childItems2.add(childData5);
        ChildItem childData6 = new ChildItem("橘子");
        childItems2.add(childData6);
        ChildItem childData7 = new ChildItem("梨子");
        childItems2.add(childData7);

        childData = new HashMap<>();
        childData.put(0, childItems);
        childData.put(1, childItems2);
        childData.put(2, childItems2);
        childData.put(3, childItems2);

        mAdapter = new ExpandableListAdapter(getContext(), groupData, childData);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        ArmsUtils.makeText(getActivity(), message);
    }

    @Override
    public void launchActivity(Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
