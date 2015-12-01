package com.china.jason.pulltorefreshviewpager.scrolltab;

import android.os.Build;

/**
 * Created by shenjie3 on 15-11-6.
 */
public class OSVersionUtils {

    public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK).intValue() < 11;

}
