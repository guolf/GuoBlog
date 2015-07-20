package cn.guolf.guoblog.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import cn.guolf.guoblog.R;

/**
 * Created by guolf on 7/17/15.
 */
public class PagedLoader extends DataSetObserver implements AbsListView.OnScrollListener, View.OnClickListener {
    private TextView normalTextView;
    private TextView finallyTextView;
    private ListAdapter adapter;
    private ListView listView;
    private ProgressWheel progressBar;
    // ListView底部View
    private View moreView;
    // 最后可见条目的索引
    private int lastVisibleIndex;
    private OnLoadListener mOnLoadListener;
    private AbsListView.OnScrollListener mOnScrollListener;
    private boolean enable;
    private boolean isLoading;
    private boolean isFinally = false;
    private Mode mode = Mode.AUTO_LOAD;

    private PagedLoader() {
    }

    public void setOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // 计算最后可见条目的索引
        lastVisibleIndex = firstVisibleItem + visibleItemCount;
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
        if (enable && mode == Mode.AUTO_LOAD &&!isLoading&& scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == listView.getAdapter().getCount()) {
            if (mOnLoadListener != null) {
                setLoading(true);
                mOnLoadListener.onLoading(this, true);
            }
        }
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }

    }

    public static Builder from(ListView listView) {
        return new Builder(listView);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean isloading) {
        if(enable) {
            isLoading = isloading;
            if (isloading) {
                progressBar.spin();
                progressBar.setVisibility(View.VISIBLE);
                normalTextView.setVisibility(View.GONE);
            } else {
                progressBar.stopSpinning();
                normalTextView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (enable && mode == Mode.CLICK_TO_LOAD) {
            setLoading(true);
            mOnLoadListener.onLoading(this, false);
        }
    }

    public CharSequence getLoadingText() {
        return normalTextView.getText();
    }

    public CharSequence getFinallyText() {
        return finallyTextView.getText();
    }

    public void setNormalText(CharSequence title) {
        normalTextView.setText(title);
    }

    public void setNormalText(int res) {
        normalTextView.setText(res);
    }

    public void setFinallyText(CharSequence text) {
        finallyTextView.setText(text);
    }

    public void setFinallyText(int res) {
        finallyTextView.setText(res);
    }

    public void setFinally() {
        if(enable) {
            isFinally = true;
            setLoading(false);
            normalTextView.setVisibility(View.GONE);
            finallyTextView.setVisibility(View.VISIBLE);
            enable = false;
        }
    }

    public void onChanged() {
        if (this.adapter == null) {
            throw new RuntimeException("must set adapter after notifyDataSetChanged");
        }
        blindEvent();
    }

    public void onInvalidated() {
        onChanged();
    }

    public ListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ListAdapter adapter) {
        if(this.adapter == null && adapter == null){
            throw  new IllegalArgumentException("adapter not to be null");
        }
        if(this.adapter != null) {
            this.adapter.unregisterDataSetObserver(this);
        }
        this.adapter = adapter;
        this.adapter.registerDataSetObserver(this);
        this.listView.setAdapter(adapter);
        blindEvent();
    }

    private void blindEvent(){
        if (adapter.getCount() == 0) {
            setEnable(false);
        } else {
            // 判断是否绑定事件，如果没有任何事件绑定，不显示foot
            if (mOnLoadListener == null) {
                setEnable(false);
            } else {
                if(!isFinally) {
                    setEnable(true);
                }
            }
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        if (enable&&isFinally) {
            normalTextView.setVisibility(View.VISIBLE);
            finallyTextView.setVisibility(View.GONE);
            isFinally = false;
        }
        setupFootView();
    }

    private void setupFootView(){
        if (enable) {
            moreView.setVisibility(View.VISIBLE);
        } else {
            moreView.setVisibility(View.GONE);
        }
    }

    private PagedLoader getPageLoader() {
        return this;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setOnScrollListener(AbsListView.OnScrollListener mOnScrollListener) {
        this.mOnScrollListener = mOnScrollListener;

    }

    public enum Mode {
        CLICK_TO_LOAD, AUTO_LOAD
    }

    public interface OnLoadListener {
        void onLoading(PagedLoader pagedLoader, boolean isAutoLoad);
    }

    public static class Builder {
        private Context mContext;
        private PagedLoader pagedLoader;

        private Builder(ListView listView) {
            this.mContext = listView.getContext();
            pagedLoader = new PagedLoader();
            // 实例化底部布局
            pagedLoader.moreView = LayoutInflater.from(mContext).inflate(R.layout.paged_foot,
                    pagedLoader.listView, false);
            pagedLoader.normalTextView = (TextView) pagedLoader.moreView.findViewById(R.id.bt_load);
            pagedLoader.finallyTextView = (TextView) pagedLoader.moreView.findViewById(R.id.bt_finally);
            pagedLoader.progressBar = (ProgressWheel)pagedLoader.moreView.findViewById(R.id.pg);
            pagedLoader.listView = listView;
        }

        public Builder setMode(Mode mode) {
            pagedLoader.setMode(mode);
            return this;
        }

        public Builder setNormalText(CharSequence text) {
            pagedLoader.setNormalText(text);
            return this;
        }

        public Builder setNormalText(int res) {
            pagedLoader.setNormalText(res);
            return this;
        }

        public Builder setFinallyText(CharSequence text) {
            pagedLoader.setFinallyText(text);
            return this;
        }

        public Builder setFinallyText(int res) {
            pagedLoader.setFinallyText(res);
            return this;
        }

        public Builder setOnLoadListener(OnLoadListener mOnLoadListener) {
            pagedLoader.setOnLoadListener(mOnLoadListener);
            return this;
        }

        public Builder setOnScrollListener(AbsListView.OnScrollListener mOnScrollListener) {
            pagedLoader.setOnScrollListener(mOnScrollListener);
            return this;
        }

        public Builder setFinallyText(String text) {
            pagedLoader.setFinallyText(text);
            return this;
        }

        public PagedLoader builder() {
            if (pagedLoader.listView.getAdapter() != null) {
                throw new RuntimeException("must set footview before set adapter");
            }
            // 加上底部View，注意要放在setAdapter方法前
            pagedLoader.listView.addFooterView(pagedLoader.moreView, null, false);
            pagedLoader.listView.setFooterDividersEnabled(false);
            pagedLoader.normalTextView.setOnClickListener(pagedLoader);
            if (pagedLoader.mode == Mode.AUTO_LOAD) {
                // 绑定监听器
                pagedLoader.listView.setOnScrollListener(pagedLoader.getPageLoader());
            }
            pagedLoader.setEnable(false);
            return pagedLoader;
        }
    }
}
