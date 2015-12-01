package com.china.jason.pulltorefreshviewpager.scrolltab.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shenjie3 on 15-11-10.
 */
public class TextViewHolder extends RecyclerView.ViewHolder{
    public TextView textView;
    public TextViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(android.R.id.text1);
    }
}
