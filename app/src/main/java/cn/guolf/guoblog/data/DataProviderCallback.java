package cn.guolf.guoblog.data;

/**
 * Created by guolf on 7/17/15.
 */
public interface DataProviderCallback<T> {
    void onLoadStart();
    void onLoadSuccess(T object);
    void onLoadFinish(int size);
    void onLoadFailure();
}