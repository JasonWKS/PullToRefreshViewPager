package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * Created by shenjie3 on 15-11-9.
 */
public class PullToRefreshFrameLayout extends PullToRefreshBase<FrameLayout> {
    private static final String TAG = "PtrFrameLayout";

    public PullToRefreshFrameLayout(Context context) {
        super(context);
    }

    public PullToRefreshFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshFrameLayout(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshFrameLayout(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected FrameLayout createRefreshableView(Context context, AttributeSet attrs) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setId(com.handmark.pulltorefresh.library.R.id.framelayout);
        return frameLayout;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return getRefreshableView().getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        View scrollChildView = getScrollChildView(getRefreshableView());
        if(scrollChildView instanceof AdapterView){
            return isLastItemVisible((AdapterView) scrollChildView);
        }else if(scrollChildView instanceof ScrollView){
            return scrollChildView.getScrollY() >= (scrollChildView.getHeight() - getHeight());
        }
        return false;
    }

    protected View getScrollChildView(ViewGroup group){
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = group.getChildAt(i);
            if(view instanceof AdapterView
                    || view instanceof ScrollView){
                return view;
            }
            if(view instanceof ViewPager){
                int cur = ((ViewPager) view).getCurrentItem();
                PagerAdapter adapter = ((ViewPager) view).getAdapter();
                if(adapter instanceof IPagerChildView){
                    return getScrollChildView((ViewGroup) ((IPagerChildView) adapter).getChildView(cur));
                }else{
                    return getScrollChildView((ViewGroup) ((ViewPager) view).getChildAt(cur));
                }
            }
            if(view instanceof ViewGroup){
                return getScrollChildView((ViewGroup) view);
            }
        }
        return null;
    }

    protected boolean isLastItemVisible(AdapterView adapterView) {
        final Adapter adapter = adapterView.getAdapter();
        if (null == adapter || adapter.isEmpty()) {
            return true;
        } else {
            final int lastItemPosition = adapterView.getCount() - 1;
            final int lastVisiblePosition = adapterView.getLastVisiblePosition();

            /**
             * This check should really just be: lastVisiblePosition ==
             * lastItemPosition, but PtRListView internally uses a FooterView
             * which messes the positions up. For me we'll just subtract one to
             * account for it and rely on the inner condition which checks
             * getBottom().
             */
            if (lastVisiblePosition >= lastItemPosition - 1) {
                final int childIndex = lastVisiblePosition - adapterView.getFirstVisiblePosition();
                final View lastVisibleChild = adapterView.getChildAt(childIndex);
                if (lastVisibleChild != null) {
                    return lastVisibleChild.getBottom() <= adapterView.getBottom();
                }
            }
        }
        return false;
    }
}
