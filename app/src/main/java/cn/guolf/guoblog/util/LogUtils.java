package cn.guolf.guoblog.util;

import android.util.Log;

import cn.guolf.guoblog.BuildConfig;

/**
 * Author：guolf on 8/7/15 15:13
 * Email ：guo@guolingfa.cn
 */
public class LogUtils {

    public static final String TAG = "cn.guolf.guoblog";

    public static final void i(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static final void w(String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static final void v(String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static final void d(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static final void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static final void e(String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg, tr);
        }
    }
}
