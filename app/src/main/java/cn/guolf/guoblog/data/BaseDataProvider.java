package cn.guolf.guoblog.data;

import android.app.Activity;

import cn.guolf.guoblog.lib.database.exception.DbException;

/**
 * Created by guolf on 7/17/15.
 */
public abstract class BaseDataProvider<T> {
    protected DataProviderCallback<T> callback;
    private Activity mActivity;

    public BaseDataProvider(Activity activity) {
        mActivity = activity;
    }

    public void setCallback(DataProviderCallback<T> callback) {
        this.callback = callback;
    }
    public Activity getActivity() {
        return mActivity;
    }
    public void setActivity(Activity activity){this.mActivity = activity;}

    public abstract void loadData(boolean startup) throws DbException;
}
