package com.china.jason.pulltorefreshviewpager.scrolltab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;

import com.china.jason.pulltorefreshviewpager.SampleGridFragment;
import com.china.jason.pulltorefreshviewpager.SampleListFragment;
import com.handmark.pulltorefresh.library.IPagerChildView;

/**
 * Created by shenjie3 on 15-11-6.
 */
public class PtrTabFragmentPagerAdapter extends FragmentPagerAdapter implements IPagerChildView {

    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    private ScrollTabHolder mListener;
    private String[] mPageTitles;

    public PtrTabFragmentPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
        mPageTitles = titles;
    }

    public void setTabHolderScrollingContent(ScrollTabHolder listener) {
        mListener = listener;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPageTitles[position];
    }

    @Override
    public int getCount() {
        return mPageTitles.length;
    }

    @Override
    public Fragment getItem(int position) {

        ScrollTabHolderFragment fragment = null;
        if (position % 2 == 0) {
            fragment = (ScrollTabHolderFragment) SampleListFragment.newInstance(position);
        } else {
            fragment = (ScrollTabHolderFragment) SampleGridFragment.newInstance(position);
        }

        mScrollTabHolders.put(position, fragment);
        if (mListener != null) {
            fragment.setScrollTabHolder(mListener);
        }

        return fragment;
    }

    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }

    private View getContentView(int position) {
        ScrollTabHolder holder = mScrollTabHolders.get(position);
        if (holder instanceof Fragment) {
            return ((Fragment) holder).getView();
        }
        return null;
    }

    @Override
    public View getChildView(int position) {
        return getContentView(position);
    }
}
