package com.vessel.gather.module.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.widght.node.LogisticsData;
import com.vessel.gather.widght.node.NodeProgressAdapter;
import com.vessel.gather.widght.node.NodeProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/12/4
 */

public class OrderDetailFragment extends MySupportFragment {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.npv_NodeProgressView)
    NodeProgressView nodeProgressView;

    List<LogisticsData> logisticsDatas;

    public static OrderDetailFragment newInstance() {
        Bundle args = new Bundle();

        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_order_detail, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("订单详情");

        logisticsDatas = new ArrayList<>();
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】已签收,签收人是【王漾】,签收网点是【忻州原平】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件到达【潍坊市中转部】,上一站是【】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【潍坊市中转部】装车,正发往【潍坊奎文代派】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件到达【潍坊】,上一站是【潍坊市中转部】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【武汉分拨中心】装车,正发往【晋江分拨中心】"));

        nodeProgressView.setNodeProgressAdapter(new NodeProgressAdapter() {

            @Override
            public int getCount() {
                return logisticsDatas.size();
            }

            @Override
            public List<LogisticsData> getData() {
                return logisticsDatas;
            }
        });
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.iv_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;
        }
    }
}