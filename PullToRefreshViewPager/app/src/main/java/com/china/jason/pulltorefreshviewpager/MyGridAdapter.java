package com.china.jason.pulltorefreshviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shenjie3 on 15-11-9.
 */
public class MyGridAdapter extends BaseAdapter {
    private List<String> mDataList = null;
    private LayoutInflater mLayoutInflater = null;

    public MyGridAdapter(Context context,List<String> dataList) {
        mDataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.grid_item,null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(mDataList.get(position));
        return convertView;
    }

    class ViewHolder{
        TextView tv;
    }
}
