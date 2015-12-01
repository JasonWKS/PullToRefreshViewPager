package com.china.jason.pulltorefreshviewpager;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.china.jason.pulltorefreshviewpager.scrolltab.ScrollTabHolderFragment;
import com.china.jason.pulltorefreshviewpager.scrolltab.grid.HeaderFooterGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenjie3 on 15-11-9.
 */
public class SampleGridFragment  extends ScrollTabHolderFragment {
    private static final String TAG = SampleGridFragment.class.getSimpleName();

    private HeaderFooterGridView mGridView;
    private List<String> mListItems = null;
    private int mPosition;

    int mHeaderHeight = 0;

    public static Fragment newInstance(int position) {
        SampleGridFragment f = new SampleGridFragment();
        Bundle b = new Bundle();
        b.putInt(SampleListFragment.ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        mPosition = b.getInt(SampleListFragment.ARG_POSITION);
        mListItems = new ArrayList<String>();
        int size = 100;
        if(mPosition == 1){
            size = 20;
        }
        for (int i = 1; i <= size; i++) {
            mListItems.add(i + ". item - current page: " + (mPosition + 1));
        }

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);

        Log.i(TAG, "poisition = " + mPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid,null);
        mGridView = (HeaderFooterGridView) view.findViewById(R.id.gridview);

        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, null);
        mGridView.addHeaderView(placeHolderView);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGridView.setOnScrollListener(new OnScroll());
        mGridView.setAdapter(new MyGridAdapter(getActivity(),mListItems));

        if(MainActivity.NEEDS_PROXY){//in my moto phone(android 2.1),setOnScrollListener do not work well
            mGridView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mScrollTabHolder != null)
                        mScrollTabHolder.onScroll(mGridView, 0, 0, 0, mPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        Log.i(TAG,"grid.scrollY = " + mGridView.getScrollY());
        Log.i(TAG,"adjustScroll, scroll = " + scrollHeight);
        if (scrollHeight == 0 && mGridView.getFirstVisiblePosition() >= 1) {
            return;
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mGridView.setSelectionFromTop(0,scrollHeight - mHeaderHeight);
        }else{

            mGridView.smoothScrollToPositionFromTop(0,scrollHeight - mHeaderHeight,0);
        }
    }

    @Override
    public void onScroll(ViewGroup view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {

    }

    public class OnScroll implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
        }
    }
}
