package com.china.jason.pulltorefreshviewpager.scrolltab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshFrameLayout;

/**
 * Created by shenjie3 on 15-11-9.
 */
public class PtrPagerSlidingTabFrameLayout extends PullToRefreshFrameLayout implements ScrollTabHolder{
    private PagerViewHeaderHolder mHeaderViewHolder;

    public PtrPagerSlidingTabFrameLayout(Context context) {
        super(context);
    }

    public PtrPagerSlidingTabFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrPagerSlidingTabFrameLayout(Context context, Mode mode) {
        super(context, mode);
    }

    public PtrPagerSlidingTabFrameLayout(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    public PagerViewHeaderHolder getHeaderViewHolder() {
        return mHeaderViewHolder;
    }

    public void setHeaderViewHolder(PagerViewHeaderHolder headerView) {
        mHeaderViewHolder = headerView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return super.isReadyForPullStart()
                && isHeaderPullStart();
    }

    private boolean isHeaderPullStart(){
        if(mHeaderViewHolder == null
                || mHeaderViewHolder.getHeaderView() == null){
            return true;
        }

        if(OSVersionUtils.NEEDS_PROXY){
            return mHeaderViewHolder.getHeaderView().getScrollY() == 0;
        }else{
            return mHeaderViewHolder.getHeaderView().getTranslationY() == 0;
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {

    }

    @Override
    public void onScroll(ViewGroup view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        mHeaderViewHolder.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount,pagePosition);
    }
}
