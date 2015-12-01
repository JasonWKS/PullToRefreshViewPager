package com.china.jason.pulltorefreshviewpager.scrolltab.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by shenjie3 on 15-11-12.
 */
public class AbsRecyclerView extends RecyclerView implements IAbsRecyclerView {

    public AbsRecyclerView(Context context) {
        super(context);
    }

    public AbsRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int getFirstVisiblePosition() {
        LayoutManager layoutManager = getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        return 0;
    }

    @Override
    public int getVisibleItemCount() {
        return getChildCount();
    }

    @Override
    public int getTotalItemCount() {
        return getLayoutManager().getItemCount();
    }

    @Override
    public void setSeletion(int position) {
        getLayoutManager().scrollToPosition(position);
    }

    @Override
    public void scrollToPositionWithOffset(int position, int scroll) {
        LayoutManager layoutManager = getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, scroll);
        }
    }

    public int getSpanCount(){
        LayoutManager layoutManager = getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return 0;
    }
}
