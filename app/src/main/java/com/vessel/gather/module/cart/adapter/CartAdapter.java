package com.vessel.gather.module.cart.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.vessel.gather.R;
import com.vessel.gather.app.data.entity.CartListResponse.CartsBean;
import com.vessel.gather.event.Event;

import org.simple.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

import static com.vessel.gather.event.Event.EVENT_CART_UPDATE;

public class CartAdapter extends DefaultAdapter<CartsBean> {

    private static final int TYPE_TITLE = 1;
    private static final int TYPE_NORMAL = 2;

    public CartAdapter(List<CartsBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<CartsBean> getHolder(View v, int viewType) {
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
    public CartsBean getItem(int position) {
        int count = -1;
        for (CartsBean entity : mInfos) {
            if (entity.isTitle()) {
                count += 1;
                if (count == position) {
                    return entity;
                } else {
                    if (count + entity.getCartDetail().size() >= position) {
                        return entity.getCartDetail().get(position - count - 1);
                    } else {
                        count = count + entity.getCartDetail().size();
                    }
                }
            }
        }
        return super.getItem(position);
    }

    @Override
    public void onBindViewHolder(BaseHolder<CartsBean> holder, int position) {
        holder.setData(getItem(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isTitle() ? TYPE_TITLE : TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (CartsBean entity : mInfos) {
            if (entity.isTitle()) {
                count = count + entity.getCartDetail().size() + 1;
            }
        }
        return count;
    }

    class MyTitle_ViewHolder extends BaseHolder<CartsBean> {

        @BindView(R.id.cart_title_checkbox)
        CheckBox checkBox;

        public MyTitle_ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(CartsBean data, int position) {
            boolean select = true;
            for (CartsBean detail : data.getCartDetail()) {
                if (!detail.isSelected()) {
                    select = false;
                    break;
                }
            }
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(select);
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                for (CartsBean detail : data.getCartDetail()) {
                    detail.setSelected(b);
                }
                EventBus.getDefault().post(new Event(), EVENT_CART_UPDATE);
            });
        }
    }

    class MyContent_ViewHolder extends BaseHolder<CartsBean> {

        @BindView(R.id.cart_detail_checkbox)
        CheckBox checkBox;

        public MyContent_ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(CartsBean data, int position) {
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(data.isSelected());
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                data.setSelected(b);
                EventBus.getDefault().post(new Event(), EVENT_CART_UPDATE);
            });
        }
    }
}
