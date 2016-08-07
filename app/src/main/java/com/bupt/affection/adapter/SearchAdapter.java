package com.bupt.affection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bupt.affection.R;

import java.util.List;

/**
 * Created by John on 15/12/23.
 */
public class SearchAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mTagList;
    private String name;

    public SearchAdapter(Context mContext, List<String> tagList) {
        this.mContext = mContext;
        this.mTagList = tagList;
    }

    @Override
    public int getCount() {
        return mTagList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTagList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_listview, viewGroup, false);
            holder = new ViewHolder();
            holder.tvParentName = (TextView) convertView.findViewById(R.id.tv_parentName);
            convertView.setTag(holder);
        }
        name = mTagList.get(position).split(":")[0];
        holder.tvParentName.setText(name);

        return convertView;
    }

    static class ViewHolder {

        TextView tvParentName;

    }


}
