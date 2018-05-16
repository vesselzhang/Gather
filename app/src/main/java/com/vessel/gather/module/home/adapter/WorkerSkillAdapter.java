package com.vessel.gather.module.home.adapter;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.vessel.gather.BuildConfig;
import com.vessel.gather.R;
import com.vessel.gather.app.data.entity.ArtisanInfoResponse.SkillsBean;
import com.vessel.gather.widght.RoundImage;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

public class WorkerSkillAdapter extends DefaultAdapter<SkillsBean> {

    public WorkerSkillAdapter(List<SkillsBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<SkillsBean> getHolder(View v, int viewType) {
        return new MemoHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.home_item_worker_skill;
    }

    class MemoHolder extends BaseHolder<SkillsBean> {

        @BindView(R.id.worker_skill_pic) RoundImage mPic;
        @BindView(R.id.worker_skill_name) TextView mName;
        @BindView(R.id.worker_skill_tip) TextView mTip;
        @BindView(R.id.worker_skill_price) TextView mPrice;

        public MemoHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(SkillsBean data, int position) {
            Observable.just(data).subscribe(address -> {
                GlideArms.with(itemView.getContext()).load(BuildConfig.APP_DOMAIN + data.getSkillPic()).into(mPic);
                mName.setText(address.getSkillName());
                mTip.setText(address.getRemark());
                mPrice.setText("￥" + address.getPrice());
            });
        }
    }
}
