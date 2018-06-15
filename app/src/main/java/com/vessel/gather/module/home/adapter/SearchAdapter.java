package com.vessel.gather.module.home.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.R;
import com.vessel.gather.app.constant.SearchType;
import com.vessel.gather.app.data.entity.SearchResponse.ArtisansBean;
import com.vessel.gather.app.data.entity.SearchResponse.ProductsBean;
import com.vessel.gather.app.data.entity.SearchResponse.ShopsBean;
import com.vessel.gather.app.data.entity.SearchResponse.SkillsBean;

import java.util.List;

public class SearchAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public SearchAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(SearchType.TYPE_PRODUCT, R.layout.home_item_seller);
        addItemType(SearchType.TYPE_ARTISAN, R.layout.home_item_worker);
        addItemType(SearchType.TYPE_SHOP, R.layout.home_item_seller);
        addItemType(SearchType.TYPE_SKILL, R.layout.home_item_worker);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case SearchType.TYPE_PRODUCT:
                if (item instanceof ProductsBean) {
                    GlideArms.with(mContext).load(BuildConfig.APP_DOMAIN + ((ProductsBean) item).getProductPic()).placeholder(R.drawable.ic_temp).into((ImageView) helper.getView(R.id.shop_ic));
                    helper.setText(R.id.shop_name, ((ProductsBean) item).getProductName());
                }
                break;
            case SearchType.TYPE_ARTISAN:
                if (item instanceof ArtisansBean) {
                    GlideArms.with(mContext).load(BuildConfig.APP_DOMAIN + ((ArtisansBean) item).getRealPhoto()).placeholder(R.drawable.ic_avatar_default).into((ImageView) helper.getView(R.id.worker_ic));
                    helper.setText(R.id.worker_name, ((ArtisansBean) item).getRealName());
                }
                break;
            case SearchType.TYPE_SHOP:
                if (item instanceof ShopsBean) {
                    GlideArms.with(mContext).load(BuildConfig.APP_DOMAIN + ((ShopsBean) item).getShopPic()).placeholder(R.drawable.ic_temp).into((ImageView) helper.getView(R.id.shop_ic));
                    helper.setText(R.id.shop_name, ((ShopsBean) item).getShopName());
                }
                break;
            case SearchType.TYPE_SKILL:
                if (item instanceof SkillsBean) {
                    GlideArms.with(mContext).load(BuildConfig.APP_DOMAIN + ((SkillsBean) item).getSkillPic()).placeholder(R.drawable.ic_avatar_default).into((ImageView) helper.getView(R.id.worker_ic));
                    helper.setText(R.id.worker_name, ((SkillsBean) item).getSkillName());
                }
                break;
        }
    }
}