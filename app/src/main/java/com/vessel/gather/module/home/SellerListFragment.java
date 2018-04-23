package com.vessel.gather.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportAdapter;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.module.home.di.DaggerSellerListComponent;
import com.vessel.gather.module.home.di.SellerListContract;
import com.vessel.gather.module.home.di.SellerListModule;
import com.vessel.gather.module.home.di.SellerListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SellerListFragment extends MySupportFragment<SellerListPresenter> implements SellerListContract.View {
    @BindView(R.id.shop_list)
    ListView mShopList;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_right)
    TextView mTitleRight;
    @BindView(R.id.iv_right)
    ImageView mImageRight;

    public static SellerListFragment newInstance() {
        Bundle args = new Bundle();

        SellerListFragment fragment = new SellerListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSellerListComponent
                .builder()
                .appComponent(appComponent)
                .sellerListModule(new SellerListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        return inflater.inflate(R.layout.home_fragment_seller_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitle.setText("优质材料");
        mTitleRight.setText("全部");
        mTitleRight.setVisibility(View.VISIBLE);
        mImageRight.setVisibility(View.VISIBLE);
        mPresenter.queryTypeList();

        mShopList.setOnItemClickListener(mPresenter);
    }

    @Override
    public void setData(@Nullable Object o) {

    }

    @Override
    public void setAdapter(MySupportAdapter adapter) {
        mShopList.setAdapter(adapter);
    }

    @Override
    public void updatePop(List<TypeListResponse.TypesBean> list, int index) {
        mTitleRight.setText(list.get(index).getTypeName());
        mPresenter.getShopList(true);
    }

    @OnClick({R.id.tv_right, R.id.iv_left})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                mPresenter.showPop(view);
                break;
            case R.id.iv_left:
                killMyself();
                break;
        }
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

    }

    @Override
    public void killMyself() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
