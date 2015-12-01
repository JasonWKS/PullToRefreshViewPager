package com.china.jason.pulltorefreshviewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.china.jason.pulltorefreshviewpager.scrolltab.OSVersionUtils;
import com.china.jason.pulltorefreshviewpager.scrolltab.PagerSlidingTabStrip;
import com.china.jason.pulltorefreshviewpager.scrolltab.PagerViewHeaderHolder;
import com.china.jason.pulltorefreshviewpager.scrolltab.PtrPagerSlidingTabFrameLayout;
import com.china.jason.pulltorefreshviewpager.scrolltab.PtrTabFragmentPagerAdapter;
import com.china.jason.pulltorefreshviewpager.scrolltab.ScrollTabHolder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

public class MainActivity extends FragmentActivity implements ScrollTabHolder, ViewPager.OnPageChangeListener {
	
	public static final boolean NEEDS_PROXY = OSVersionUtils.NEEDS_PROXY;

	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	private ViewPager mViewPager;
	private PtrTabFragmentPagerAdapter mPagerAdapter;
    private PtrPagerSlidingTabFrameLayout mRefreshFrameLayout;

	private TextView info;
    private TextView mTextView_Header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
        mRefreshFrameLayout = (PtrPagerSlidingTabFrameLayout) findViewById(R.id.parent);

        View mHeader = findViewById(R.id.header);
        PagerViewHeaderHolder holder = new PagerViewHeaderHolder(this,mHeader);
        mRefreshFrameLayout.setHeaderViewHolder(holder);
		info = (TextView) findViewById(R.id.info);
        mTextView_Header = (TextView) findViewById(R.id.tv_header);
        mTextView_Header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "zzzzz", Toast.LENGTH_SHORT).show();
            }
        });

		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(4);

        String[] titles = getPagerTitles();
		mPagerAdapter = new PtrTabFragmentPagerAdapter(getSupportFragmentManager(),titles);
		mPagerAdapter.setTabHolderScrollingContent(this);

		mViewPager.setAdapter(mPagerAdapter);

		mPagerSlidingTabStrip.setViewPager(mViewPager);
		mPagerSlidingTabStrip.setOnPageChangeListener(this);

        mRefreshFrameLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<FrameLayout>() {
            @Override
            public void onRefresh(PullToRefreshBase<FrameLayout> refreshView) {
                mHandler.sendEmptyMessageDelayed(0,5000);
            }
        });

    }

    private String[] getPagerTitles(){
        String[] arr = new String[4];
        arr[0] = "page0";
        arr[1] = "page1";
        arr[2] = "page2";
        arr[3] = "page3";
        return arr;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mRefreshFrameLayout.onRefreshComplete();
                    break;
            }
        }
    };

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// nothing
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// nothing
	}

	@Override
	public void onPageSelected(int position) {
		SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
		ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        PagerViewHeaderHolder headerViewHolder =  mRefreshFrameLayout.getHeaderViewHolder();
        View headerView = headerViewHolder.getHeaderView();
        if(NEEDS_PROXY){
            //TODO is not good
			currentHolder.adjustScroll(headerView.getHeight()-headerViewHolder.getLastY());
			headerView.postInvalidate();
		}else{
			currentHolder.adjustScroll((int) (headerView.getHeight() + headerView.getTranslationY()));
		}
	}

	@Override
	public void onScroll(ViewGroup view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
		if (mViewPager.getCurrentItem() == pagePosition) {
			mRefreshFrameLayout.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount,pagePosition);
		}
	}

	@Override
	public void adjustScroll(int scrollHeight) {
		// nothing
	}

	public static float clamp(float value, float max, float min) {
		return Math.max(Math.min(value, min), max);
	}
}
