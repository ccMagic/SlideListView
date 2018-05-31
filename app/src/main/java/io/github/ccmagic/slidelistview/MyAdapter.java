package io.github.ccmagic.slidelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ccMagic on 2018/5/21.
 * Copyright ：
 * Version ：
 * Reference ：
 * Description ：
 */
public class MyAdapter extends BaseAdapter {

    private List<String> mData;
    private LayoutInflater mLayoutInflater;

    public MyAdapter(Context context, List<String> list) {
        mData = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.adapter_layout, parent, false);
            viewHolder.tv = convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(mData.get(position));
        return convertView;
    }

    private static final class ViewHolder {
        TextView tv;
    }
}
