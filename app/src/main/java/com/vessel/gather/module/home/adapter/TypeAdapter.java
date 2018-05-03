package com.vessel.gather.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportAdapter;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.app.utils.UiHelper;

public class TypeAdapter extends MySupportAdapter<TypeListResponse.TypesBean> {

    public TypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_type, null);
            viewHolder.mListItem = UiHelper.find(convertView, R.id.list_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mListItem.setText(getItem(position).getTypeName());
        return convertView;
    }

    class ViewHolder {
        TextView mListItem;
    }
}