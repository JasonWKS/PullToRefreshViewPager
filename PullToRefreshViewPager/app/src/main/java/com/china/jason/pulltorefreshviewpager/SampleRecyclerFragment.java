package com.china.jason.pulltorefreshviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.china.jason.pulltorefreshviewpager.scrolltab.ScrollTabHolderFragment;
import com.china.jason.pulltorefreshviewpager.scrolltab.recycler.AbsRecyclerView;
import com.china.jason.pulltorefreshviewpager.scrolltab.recycler.HeaderNumberdAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenjie3 on 15-11-12.
 */
public class SampleRecyclerFragment extends ScrollTabHolderFragment {
    private static final String TAG = SampleRecyclerFragment.class.getSimpleName();

    private AbsRecyclerView mRecyclerView;
    private List<String> mListItems = null;
    private int mPosition;

    int mHeaderHeight = 0;

    public static Fragment newInstance(int position) {
        SampleRecyclerFragment f = new SampleRecyclerFragment();
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
        for (int i = 1; i <= 100; i++) {
            mListItems.add(i + ". item - current page: " + (mPosition + 1));
        }

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);

        Log.i(TAG, "poisition = " + mPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler,null);
        mRecyclerView = (AbsRecyclerView) view.findViewById(R.id.recycler_view);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, null);
        final HeaderNumberdAdapter adapter
                = new HeaderNumberdAdapter(placeHolderView, mListItems);
        mRecyclerView.setAdapter(adapter);

        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? mRecyclerView.getSpanCount() : 1;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.addOnScrollListener(new OnScroll());
        if(MainActivity.NEEDS_PROXY){//in my moto phone(android 2.1),setOnScrollListener do not work well
            mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mScrollTabHolder != null)
                        mScrollTabHolder.onScroll(mRecyclerView, 0, 0, 0, mPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        Log.i(TAG, "grid.scrollY = " + mRecyclerView.getScrollY());
        Log.i(TAG, "adjustScroll, scroll = " + scrollHeight);
        mRecyclerView.scrollToPositionWithOffset(0, scrollHeight - mHeaderHeight);
    }

    @Override
    public void onScroll(ViewGroup view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {

    }

    public class OnScroll extends RecyclerView.OnScrollListener {

        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(recyclerView,
                        mRecyclerView.getFirstVisiblePosition(),
                        mRecyclerView.getChildCount(),
                        mRecyclerView.getTotalItemCount(), mPosition);
        }
    }
}
