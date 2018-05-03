package com.vessel.gather.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.http.imageloader.glide.GlideArms;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportAdapter;
import com.vessel.gather.app.data.entity.ServiceListResponse.ShopsBean;
import com.vessel.gather.app.utils.UiHelper;

public class SellerListAdapter extends MySupportAdapter<ShopsBean> {

    public SellerListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_seller, null);
            viewHolder.chaptersPic = UiHelper.find(convertView, R.id.shop_ic);
            viewHolder.name = UiHelper.find(convertView, R.id.shop_name);
//            viewHolder.score = UiHelper.find(convertView, R.id.score);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ShopsBean shopsBean = getItem(position);

        GlideArms.with(parent.getContext()).load(BuildConfig.APP_DOMAIN + shopsBean.getShopPic()).placeholder(R.drawable.ic_temp).into(viewHolder.chaptersPic);
        viewHolder.name.setText(shopsBean.getShopName());
//        viewHolder.score.setRating(getItem(position).getScore());
        return convertView;
    }

    class ViewHolder {
        ImageView chaptersPic;
        TextView name;
    }
}
