package cn.guolf.guoblog.data;

import android.app.Activity;

/**
 * Created by guolf on 7/17/15.
 */
public abstract class BaseDataProvider<T> {
    private Activity mActivity;
    protected DataProviderCallback<T> callback;

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

    public abstract void loadData(boolean startup);
}
