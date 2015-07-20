package cn.guolf.guoblog.processers;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
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
    ActionBarActivity getActivity();
    void setProvider(DataProvider provider);
    void setActivity(ActionBarActivity activity);
    boolean onOptionsItemSelected(MenuItem item);
    void onConfigurationChanged(Configuration newConfig);
    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);
}