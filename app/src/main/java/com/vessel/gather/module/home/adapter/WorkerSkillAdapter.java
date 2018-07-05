package com.vessel.gather.module.home.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.R;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse.SkillsBean;

import java.util.List;

import io.reactivex.Observable;

public class WorkerSkillAdapter extends BaseQuickAdapter<SkillsBean, BaseViewHolder> {

    public WorkerSkillAdapter(List<SkillsBean> infos) {
        super(R.layout.home_item_worker_skill, infos);
    }

    @Override
    protected void convert(BaseViewHolder helper, SkillsBean item) {
        Observable.just(item).subscribe(address -> {
            helper.addOnClickListener(R.id.worker_skill_pic);
            ImageView image = helper.getView(R.id.worker_skill_pic);
            GlideArms.with(mContext).load(BuildConfig.APP_DOMAIN + address.getSkillPic()).into(image);
            helper.setText(R.id.worker_skill_name, address.getSkillName());
            helper.setText(R.id.worker_skill_tip, address.getRemark());
            helper.setText(R.id.worker_skill_price, "¥" + address.getPrice());
        });
    }
}
