package com.vessel.gather.app.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jimq
 * @date 2017/12/3
 */
public abstract class MySupportAdapter<T> extends BaseAdapter {

    protected List<T> mList = new ArrayList<>();
    protected Context mContext;

    public MySupportAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    public T getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    abstract public View getView(int position, View convertView,
                                 ViewGroup parent);

    public void setList(List<T> list) {
        this.mList.clear();
        if (null != list) {
            this.mList.addAll(list);
        }
    }

    public void addList(List<T> list) {
        if (null != list) {
            this.mList.addAll(list);
        }
    }

    public List<T> getList() {
        return mList;
    }

}
