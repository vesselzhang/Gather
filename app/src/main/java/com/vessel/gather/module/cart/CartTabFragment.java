package com.vessel.gather.module.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.data.entity.CartListResponse.CartsBean;
import com.vessel.gather.app.utils.RecycleViewDivider;
import com.vessel.gather.event.Event;
import com.vessel.gather.module.cart.adapter.CartAdapter;
import com.vessel.gather.module.cart.di.CartContract;
import com.vessel.gather.module.cart.di.CartModule;
import com.vessel.gather.module.cart.di.CartPresenter;
import com.vessel.gather.module.cart.di.DaggerCartComponent;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vessel.gather.event.Event.EVENT_CART_UPDATE;

public class CartTabFragment extends MySupportFragment<CartPresenter> implements CartContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.cart_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.cart_select_all)
    CheckBox selectAll;
    @BindView(R.id.cart_cost)
    TextView cost;
    @BindView(R.id.cart_pay)
    Button submit;

    @Inject
    CartAdapter mAdapter;
    @Inject
    List<CartsBean> mList;

    int count, money;

    public static CartTabFragment newInstance() {
        Bundle args = new Bundle();

        CartTabFragment fragment = new CartTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCartComponent
                .builder()
                .appComponent(appComponent)
                .cartModule(new CartModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_fragment, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("购物车");
        selectAll.setOnCheckedChangeListener(this);
        initRecycleView();


        mPresenter.cartList();
    }

    private void initRecycleView() {
        ArmsUtils.configRecyclerView(mRecyclerView, new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL
                , 20, getResources().getColor(R.color.def_bg)));
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick(R.id.cart_pay)
    void OnClick() {
        if (count > 0) {
            ARouter.getInstance().build("/app/order/pay").navigation();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String s) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Subscriber(tag = EVENT_CART_UPDATE)
    void updateList(Event event) {
        count = 0;
        money = 0;
        for (CartsBean cartsBean : mList) {
            if (cartsBean.isTitle()) {
                for (CartsBean detail : cartsBean.getCartDetail()) {
                    if (detail.isSelected()) {
                        count++;
                        money += detail.getNum() * detail.getPrice();
                    }
                }
            }
        }
        cost.setText(money + "");
        submit.setText("结算 " + count);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        for (CartsBean cartsBean : mList) {
            for (CartsBean detail : cartsBean.getCartDetail()) {
                detail.setSelected(b);
            }
            cartsBean.setSelected(b);
        }
        mAdapter.notifyDataSetChanged();
        updateList(null);
    }
}
