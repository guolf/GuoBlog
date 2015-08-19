package cn.guolf.guoblog.processers;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import cn.guolf.guoblog.data.BaseDataProvider;

/**
 * Created by guolf on 7/17/15.
 */
public interface BaseProcesser<E,DataProvider extends BaseDataProvider<E>> {

    void onResume();
    void onPause();
    void onDestroy();
    void assumeView(View view);
    void loadData(boolean startup);

    AppCompatActivity getActivity();

    void setActivity(AppCompatActivity activity);

    void setProvider(DataProvider provider);

    boolean onOptionsItemSelected(MenuItem item);
    void onConfigurationChanged(Configuration newConfig);
    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);
}