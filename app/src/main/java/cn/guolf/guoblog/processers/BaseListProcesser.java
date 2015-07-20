package cn.guolf.guoblog.processers;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.List;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.adapter.BaseAdapter;
import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.lib.kits.PrefKit;
import cn.guolf.guoblog.lib.kits.Toolkit;
import cn.guolf.guoblog.widget.PagedLoader;

/**
 * Created by guolf on 7/17/15.
 */
public class BaseListProcesser<DataType,DataProvider extends ListDataProvider<DataType,? extends BaseAdapter<DataType>>>
        extends BaseProcesserImpl<List<DataType>,DataProvider>
        implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private PagedLoader mLoader;
    private SwipeRefreshLayout mSwipeLayout;
    private TextView headView;

    public BaseListProcesser(DataProvider provider) {
        super(provider);
    }

    @Override
    public void assumeView(View view) {
        this.listView = (ListView) view.findViewById(android.R.id.list);
        this.mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        this.mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        this.mSwipeLayout.setOnRefreshListener(this);
        this.mSwipeLayout.setColorSchemeColors(colorPrimary, colorPrimaryDark,colorAccent);
        this.headView = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.type_head, listView, false);
        this.headView.setText("类型：" + provider.getTypeName());
        this.listView.addHeaderView(headView, null, false);
        PagedLoader.OnLoadListener loadListener = new PagedLoader.OnLoadListener() {
            @Override
            public void onLoading(PagedLoader pagedLoader, boolean isAutoLoad) {
                BaseListProcesser.this.provider.loadNextData();
            }
        };
        this.mLoader = PagedLoader.from(listView).setFinallyText(R.string.end).setOnLoadListener(loadListener).builder();
        this.mLoader.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        this.mLoader.setAdapter(this.provider.getAdapter());
        this.listView.setOnItemClickListener(getOnItemClickListener());
        this.listView.setOnItemLongClickListener(getOnItemLongClickListener());
    }

    private AdapterView.OnItemLongClickListener getOnItemLongClickListener() {
        return provider.getOnItemLongClickListener();
    }

    private AdapterView.OnItemClickListener getOnItemClickListener() {
        return provider.getOnItemClickListener();
    }

    @Override
    public void loadData(final boolean startup) {
        Toolkit.runInUIThread(new Runnable() {
            @Override
            public void run() {
                provider.loadData(startup);
                if (!provider.isCached() || PrefKit.getBoolean(mActivity, mActivity.getString(R.string.pref_auto_reflush_key), false)) {
                    mSwipeLayout.setRefreshing(true);
                    onRefresh();
                }
            }
        }, startup ? 260 : 0);
    }

    public void onResume() {
        if (PrefKit.getBoolean(mActivity, mActivity.getString(R.string.pref_auto_page_key), false)) {
            this.mLoader.setMode(PagedLoader.Mode.AUTO_LOAD);
        } else {
            this.mLoader.setMode(PagedLoader.Mode.CLICK_TO_LOAD);
        }
    }

    @Override
    public void onRefresh() {
        if(mLoader.getAdapter().getCount()>0) {
            mLoader.setEnable(true);
        }
        provider.loadNewData();
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadSuccess(List<DataType> items) {

    }

    @Override
    public void onLoadFinish(int size) {
        provider.getAdapter().notifyDataSetChanged();
        if (provider.getAdapter().getCount()<provider.getPageSize()||size==0) {
            mLoader.setFinally();
        } else {
            mLoader.setLoading(false);
        }
        if (mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(false);
        }
    }

    public ListView getListView() {
        return listView;
    }

    public PagedLoader getLoader() {
        return mLoader;
    }

    public SwipeRefreshLayout getSwipeLayout() {
        return mSwipeLayout;
    }

    @Override
    public void onLoadFailure() {

    }

    public DataProvider getProvider() {
        return provider;
    }

    public void setHeadViewText(String type){
        if(headView!=null) {
            headView.setText("类型：" + type);
        }
    }
}
