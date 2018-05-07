package com.vessel.gather.module.me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.vessel.gather.R;

import java.util.List;
import java.util.Map;

/**
 * @author vesselzhang
 * @date 2018/3/7
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> groupTitle;
    //子项是一个map，key是group的id，每一个group对应一个ChildItem的list
    private Map<Integer, List<ChildItem>> childMap;
    private boolean showCheckbox;

    public ExpandableListAdapter(Context context, List<String> groupTitle, Map<Integer, List<ChildItem>> childMap) {
        this.mContext = context;
        this.groupTitle = groupTitle;
        this.childMap = childMap;
    }

    public void setShowCheckbox(boolean showCheckbox) {
        this.showCheckbox = showCheckbox;
    }

    /*
     *  Gets the data associated with the given child within the given group
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //我们这里返回一下每个item的名称，以便单击item时显示
        return childMap.get(groupPosition).get(childPosition).getTitle();
    }

    /*
     * 取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /*
     *  Gets a View that displays the data for the given child within the given group
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_me_collect_detail, null);
            childHolder = new ChildHolder();
            childHolder.checkBox = convertView.findViewById(R.id.collect_item_detail_checkbox);
//            childHolder.childText = (TextView) convertView.findViewById(R.id.tv_child_text);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        childHolder.checkBox.setVisibility(showCheckbox ? View.VISIBLE : View.GONE);
//        childHolder.childImg.setBackgroundResource(childMap.get(groupPosition).get(childPosition).getMarkerImgId());
//        childHolder.childText.setText(childMap.get(groupPosition).get(childPosition).getTitle());

        return convertView;
    }

    /*
     * 取得指定分组的子元素数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return childMap.get(groupPosition).size();
    }

    /**
     * 取得与给定分组关联的数据
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groupTitle.get(groupPosition);
    }

    /**
     * 取得分组数
     */
    @Override
    public int getGroupCount() {
        return groupTitle.size();
    }

    /**
     * 取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /*
     *Gets a View that displays the given group
     *return: the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_me_collect_title, null);
            groupHolder.groupText = convertView.findViewById(R.id.collect_item_title_text);
            groupHolder.groupImage = convertView.findViewById(R.id.collect_item_title_image);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.groupText.setText(groupTitle.get(groupPosition));
        if (isExpanded) {
            groupHolder.groupImage.setBackgroundResource(R.drawable.icon_down);
        } else {
            groupHolder.groupImage.setBackgroundResource(R.drawable.icon_next);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // Indicates whether the child and group IDs are stable across changes to the underlying data
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // Whether the child at the specified position is selectable
        return true;
    }

    /**
     * show the text on the child and group item
     */
    private class GroupHolder {
        TextView groupText;
        ImageView groupImage;
    }

    private class ChildHolder {
        CheckBox checkBox;
    }
}