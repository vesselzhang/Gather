package com.vessel.gather.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportAdapter;
import com.vessel.gather.app.data.entity.ServiceListResponse;

public class WorkerListAdapter extends MySupportAdapter<ServiceListResponse.ArtisansBean> {

    public WorkerListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_worker, null);
//            viewHolder.chaptersPic = UiHelper.find(convertView, R.id.shop_ic);
//            viewHolder.name = UiHelper.find(convertView, R.id.shop_name);
//            viewHolder.score = UiHelper.find(convertView, R.id.score);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Glide.with(parent.getContext()).load(BuildConfig.APP_DOMAIN + getItem(0).getShopPic()).into(viewHolder.chaptersPic);
//        viewHolder.name.setText(getItem(0).getShopName());
//        viewHolder.score.setRating(getItem(position).getScore());
        return convertView;
    }

    class ViewHolder {
        ImageView chaptersPic;
        TextView name;
    }
}
