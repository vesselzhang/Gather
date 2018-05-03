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
import com.vessel.gather.app.data.entity.ServiceListResponse.ArtisansBean;
import com.vessel.gather.app.utils.UiHelper;

public class WorkerListAdapter extends MySupportAdapter<ArtisansBean> {

    public WorkerListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_worker, null);
            viewHolder.chaptersPic = UiHelper.find(convertView, R.id.worker_ic);
            viewHolder.name = UiHelper.find(convertView, R.id.worker_name);
//            viewHolder.score = UiHelper.find(convertView, R.id.score);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ArtisansBean artisansBean = getItem(position);

        GlideArms.with(parent.getContext()).load(BuildConfig.APP_DOMAIN + artisansBean.getRealPhoto()).placeholder(R.drawable.ic_avatar_default).into(viewHolder.chaptersPic);
        viewHolder.name.setText(artisansBean.getRealName());
//        viewHolder.score.setRating(getItem(position).getScore());
        return convertView;
    }

    class ViewHolder {
        ImageView chaptersPic;
        TextView name;
    }
}
