package com.vessel.gather.module.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.addresspicker.huichao.addresspickerlibrary.CityPickerDialog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.constant.Constants;
import com.vessel.gather.app.constant.SPConstant;
import com.vessel.gather.app.data.entity.IndexResponse;
import com.vessel.gather.app.utils.ProvinceUtils;
import com.vessel.gather.widght.AutoScrollViewPager;
import com.vessel.gather.widght.BaseViewPagerAdapter;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public class HomeTabFragment extends MySupportFragment {
    private static final int REQUEST_CODE_PICK_CITY = 0;

    @BindView(R.id.city)
    TextView cityTV;
    @BindView(R.id.view_pager)
    AutoScrollViewPager mViewPager;

    private BaseViewPagerAdapter<String> mBaseViewPagerAdapter;
    private List<String> imageUrls = new ArrayList<>();
    private List<IndexResponse.BannersBean> banners = new ArrayList<>();

    private String token;

    public static HomeTabFragment newInstance() {
        Bundle args = new Bundle();

        HomeTabFragment fragment = new HomeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBaseViewPagerAdapter = new BaseViewPagerAdapter<String>(getActivity().getApplicationContext(), listener) {
            @Override
            public void loadImage(ImageView view, int position, String url) {
                Glide.with(getActivity().getApplicationContext()).load(url).into(view);
            }

            @Override
            public void setSubTitle(TextView textView, int position, String s) {
                textView.setText(s);
            }
        };
        mViewPager.setAdapter(mBaseViewPagerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        token = DataHelper.getStringSF(getActivity(), SPConstant.SP_TOKEN);
    }

    @Override
    public void setData(Object data) {

    }

    @Subscriber(tag = Constants.TAG_BANNERS_REFRESH)
    public void onRefresh(IndexResponse indexResponse) {
        banners.clear();
        banners.addAll(indexResponse.getBanners());
        for (IndexResponse.BannersBean bean : indexResponse.getBanners()) {
            imageUrls.add(bean.getBannerPic());
        }
        mBaseViewPagerAdapter.add(imageUrls);
    }

    BaseViewPagerAdapter.OnAutoViewPagerItemClickListener listener = (position, o) -> {
        int type = banners.get(position).getLinkType();
        ARouter.getInstance().build("/app/web").navigation();
    };

    @OnClick({R.id.layout_shangjia, R.id.layout_shifu, R.id.layout_shangjia_apply, R.id.layout_shifu_apply,
            R.id.ll_search, R.id.tuijianshangjia, R.id.tuijianshifu, R.id.location, R.id.ll_agj, R.id.ll_bmqd})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tuijianshangjia:
            case R.id.layout_shangjia:
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_SELLER_LIST)
                        .navigation();
                break;
            case R.id.layout_shifu:
            case R.id.tuijianshifu:
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_WORKER_LIST)
                        .navigation();
                break;
            case R.id.layout_shangjia_apply:
                if (TextUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/app/login").navigation();
                    return;
                }
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_SELLER_APPLY)
                        .navigation();
                break;
            case R.id.layout_shifu_apply:
                if (TextUtils.isEmpty(token)) {
                    ARouter.getInstance().build("/app/login").navigation();
                    return;
                }
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_WORKER_APPLY)
                        .navigation();
                break;
            case R.id.location:
                showAddressDialog();
//                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class), REQUEST_CODE_PICK_CITY);
                break;
            case R.id.ll_search:
                ARouter.getInstance().build("/app/container")
                        .withSerializable(Constants.PAGE, Constants.PAGE_SEARCH)
                        .navigation();
                break;
            case R.id.ll_agj:
                ArmsUtils.makeText(getActivity(), "敬请期待");
                break;
            case R.id.ll_bmqd:
                ArmsUtils.makeText(getActivity(), "敬请期待");
                break;

        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
//            if (data != null) {
//                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
//                cityTV.setText(city);
//            }
//        }
//    }

    private void showAddressDialog() {
        new CityPickerDialog(getActivity(), ProvinceUtils.getInstance().getProvinces(),
                null, null, null, (selectProvince, selectCity, selectCounty) -> {
            StringBuilder address = new StringBuilder();
            address
//                    .append(selectProvince != null ? selectProvince
//                            .getAreaName() : "")
                    .append(selectCity != null ? selectCity
                            .getAreaName() : "");
            cityTV.setText(address.toString());
        }).show();
    }
}
