package cn.guolf.guoblog.processers;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cn.guolf.guoblog.MyApplication;
import cn.guolf.guoblog.R;
import cn.guolf.guoblog.adapter.BaseAdapter;
import cn.guolf.guoblog.data.ArticleCacheHandler;
import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.entity.ArticleItem;

/**
 * Created by guolf on 7/17/15.
 */
public class ArticleListProcesser<DataProvider extends ListDataProvider<ArticleItem,? extends BaseAdapter<ArticleItem>>>
        extends BaseListProcesser<ArticleItem,DataProvider> {
    private ArticleCacheHandler handler;

    public ArticleListProcesser(DataProvider provider) {
        super(provider);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_cache){
            if(handler==null) {
                handler = new ArticleCacheHandler(getActivity());
            }
            handler.setCacheList(getProvider().getAdapter().getDataSet());
            handler.start();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_list,menu);
    }

    @Override
    public void onDestroy() {
        if(handler!=null){
            handler.stop();
            handler.cleanNotification();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MyApplication.getInstance().isListImageShowStatusChange()) {
            provider.getAdapter().notifyDataSetChanged(true);
        }
    }
}
