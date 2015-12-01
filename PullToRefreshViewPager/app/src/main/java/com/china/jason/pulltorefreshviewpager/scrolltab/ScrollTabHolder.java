package com.china.jason.pulltorefreshviewpager.scrolltab;

import android.view.ViewGroup;

public interface ScrollTabHolder {

	void adjustScroll(int scrollHeight);

	void onScroll(ViewGroup view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);

}
