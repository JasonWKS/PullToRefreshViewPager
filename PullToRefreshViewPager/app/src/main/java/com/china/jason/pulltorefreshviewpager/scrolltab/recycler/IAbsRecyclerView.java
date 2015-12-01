package com.china.jason.pulltorefreshviewpager.scrolltab.recycler;

/**
 * Created by shenjie3 on 15-11-12.
 */
public interface IAbsRecyclerView {
    public int getFirstVisiblePosition();
    public int getVisibleItemCount();
    public int getTotalItemCount();
    public void setSeletion(int position);
    public void scrollToPositionWithOffset(int position, int scroll);
}
