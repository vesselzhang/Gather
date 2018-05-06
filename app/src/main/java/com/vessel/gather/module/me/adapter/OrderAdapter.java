package com.vessel.gather.module.me.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.vessel.gather.R;
import com.vessel.gather.app.data.entity.OrderListResponse.OrdersBean.OrderDetailBean;

import java.util.List;

import butterknife.BindView;

public class OrderAdapter extends DefaultAdapter<OrderDetailBean> {

    private static final int TYPE_TITLE = 1;
    private static final int TYPE_NORMAL = 2;

    public OrderAdapter(List<OrderDetailBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<OrderDetailBean> getHolder(View v, int viewType) {
        BaseHolder holder = null;
        switch (viewType) {
            case TYPE_TITLE:
                holder = new MyTitle_ViewHolder(v);
                break;
            case TYPE_NORMAL:
                holder = new MyContent_ViewHolder(v);
                break;
        }
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                return R.layout.cart_item_title;
            case TYPE_NORMAL:
                return R.layout.cart_item_detail;
            default:
                try {
                    throw new Exception("exp");
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
        }
    }

    @Override
    public OrderDetailBean getItem(int position) {
        int count = -1;
        for (OrderDetailBean entity : mInfos) {
            if (entity.isTitle()) {
                count += 1;
                if (count == position) {
                    return entity;
                } else {
                    if (count + entity.getContent().size() >= position) {
                        return entity.getContent().get(position - count - 1);
                    } else {
                        count = count + entity.getContent().size();
                    }
                }
            }
        }
        return super.getItem(position);
    }

    @Override
    public void onBindViewHolder(BaseHolder<OrderDetailBean> holder, int position) {
        holder.setData(getItem(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isTitle() ? TYPE_TITLE : TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (OrderDetailBean entity : mInfos) {
            if (entity.isTitle()) {
                count = count + entity.getContent().size() + 1;
            }
        }
        return count;
    }

    class MyTitle_ViewHolder extends BaseHolder<OrderDetailBean> {

        @BindView(R.id.cart_title_checkbox)
        CheckBox checkBox;

        public MyTitle_ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(OrderDetailBean data, int position) {
            checkBox.setVisibility(View.GONE);
        }
    }

    class MyContent_ViewHolder extends BaseHolder<OrderDetailBean> {

        @BindView(R.id.cart_detail_checkbox)
        CheckBox checkBox;

        public MyContent_ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(OrderDetailBean data, int position) {
            checkBox.setVisibility(View.GONE);
        }
    }
}
