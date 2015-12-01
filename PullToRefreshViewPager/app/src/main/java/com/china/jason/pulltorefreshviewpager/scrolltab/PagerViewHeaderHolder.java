package com.china.jason.pulltorefreshviewpager.scrolltab;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.china.jason.pulltorefreshviewpager.R;
import com.china.jason.pulltorefreshviewpager.scrolltab.recycler.IAbsRecyclerView;

/**
 * Created by shenjie3 on 15-11-9.
 */
public class PagerViewHeaderHolder implements ScrollTabHolder{
    private Context mContext;
    private View mHeaderView;

    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private int mLastY;

    public PagerViewHeaderHolder(Context context,View headerView){
        mContext = context;
        mHeaderView = headerView;
        init();
    }

    private void init(){
        Resources resources = mContext.getResources();
        mMinHeaderHeight = resources.getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = resources.getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight;
        mLastY = 0;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    @Override
    public void onScroll(ViewGroup view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        int scrollY = getScrollY(view);
        if(OSVersionUtils.NEEDS_PROXY){
            //TODO is not good
            mLastY=-Math.max(-scrollY, mMinHeaderTranslation);
            mHeaderView.scrollTo(0, mLastY);
            mHeaderView.postInvalidate();
        }else{
            mHeaderView.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        // nothing
    }

    public int getScrollY(ViewGroup view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = 0;
        if(view instanceof AbsListView){
            firstVisiblePosition = ((AbsListView)view).getFirstVisiblePosition();
        }else if(view instanceof IAbsRecyclerView){
            firstVisiblePosition = ((IAbsRecyclerView)view).getFirstVisiblePosition();
        }

        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public int getLastY() {
        return mLastY;
    }
}
