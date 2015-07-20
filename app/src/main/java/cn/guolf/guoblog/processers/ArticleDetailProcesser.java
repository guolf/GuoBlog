package cn.guolf.guoblog.processers;

import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import cn.guolf.guoblog.data.DataProviderCallback;
import cn.guolf.guoblog.data.impl.ArticleDetailProvider;
import cn.guolf.guoblog.fragments.ArticleDetailFragment;

/**
 * Created by guolf on 7/19/15.
 */
public class ArticleDetailProcesser extends BaseProcesserImpl<String, ArticleDetailProvider>
        implements DataProviderCallback<String> {

    private ArticleDetailFragment.NewsDetailCallBack callBack;

    public ArticleDetailProcesser(ArticleDetailProvider provider){
        super(provider);
    }

    @Override
    public void assumeView(View view) {

    }

    @Override
    public void loadData(boolean startup) {
        super.loadData(startup);
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadFailure() {

    }

    @Override
    public void onLoadFinish(int size) {

    }

    @Override
    public void onLoadSuccess(String object) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return menuCallBack != null && menuCallBack.onMenuSelect(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void setCallBack(ArticleDetailFragment.NewsDetailCallBack callBack){
        this.callBack=callBack;
    }

    public void setArticleItem(int sid,String title){

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && client.myCallback != null) {
//            client.onHideCustomView();
//            return true;
//        }
        return false;
    }
}
