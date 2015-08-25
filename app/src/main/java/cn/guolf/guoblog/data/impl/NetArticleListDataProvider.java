package cn.guolf.guoblog.data.impl;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.ResponseHandlerInterface;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import cn.guolf.guoblog.MyApplication;
import cn.guolf.guoblog.R;
import cn.guolf.guoblog.activity.ArticleDetailActivity;
import cn.guolf.guoblog.adapter.ArticleListAdapter;
import cn.guolf.guoblog.entity.ArticleItem;
import cn.guolf.guoblog.entity.ArticleListObject;
import cn.guolf.guoblog.entity.ResponseObject;
import cn.guolf.guoblog.fragments.ArticleDetailFragment;
import cn.guolf.guoblog.lib.CroutonStyle;
import cn.guolf.guoblog.lib.database.exception.DbException;
import cn.guolf.guoblog.lib.handler.BaseHttpResponseHandler;
import cn.guolf.guoblog.lib.kits.FileCacheKit;
import cn.guolf.guoblog.lib.kits.NetKit;
import cn.guolf.guoblog.lib.kits.Toolkit;

/**
 * Created by guolf on 7/17/15.
 */
public abstract class NetArticleListDataProvider extends BaseArticleListDataProvider<ArticleListAdapter> {

    private String topSid;
    private int current;
    private List<String> ids = new ArrayList<>();

    private ResponseHandlerInterface newsPage = new BaseHttpResponseHandler<ArticleListObject>(
            new TypeToken<ResponseObject<ArticleListObject>>() {
            }) {

        @Override
        protected void onSuccess(ArticleListObject result) {
            List<ArticleItem> itemList = result.getData();
            List<ArticleItem> dataSet = getAdapter().getDataSet();
            int size = 0;
            boolean find = false;
            if (!find) {
                size++;
            }

            if (!hasCached) {
                hasCached = true;
                getAdapter().setDataSet(itemList);
                if (itemList.size() > 2) {
                    topSid = itemList.get(1).getArticleId();
                }
                showToastAndCache(itemList, size - 1);
            } else {
                dataSet.addAll(itemList);
                for (ArticleItem i : itemList) {
                    ids.add(i.getArticleId());
                }
                showToastAndCache(itemList, size - 1);
            }
        }

        @Override
        protected Activity getActivity() {
            return NetArticleListDataProvider.this.getActivity();
        }

        @Override
        public void onFinish() {
            if (callback != null) {
                callback.onLoadFinish(40);
            }
        }
    };


    public NetArticleListDataProvider(Activity mActivity) {
        super(mActivity);
        hasCached = false;
    }

    @Override
    public ArticleListAdapter newAdapter() {
        return new ArticleListAdapter(getActivity(), new ArrayList<ArticleItem>());
    }

    @Override
    public void loadNewData() {
        if (getIsCollection()) {
            loadData(false);
            return;
        }
        makeRequest(1, getTypeKey(), newsPage);
    }

    @Override
    public void loadNextData() {
        makeRequest(current + 1, getTypeKey(), newsPage);
    }

    public void makeRequest(int page, String type, ResponseHandlerInterface handlerInterface) {
        NetKit.getInstance().getArticlelistByPage(page, type, handlerInterface);
    }

    @Override
    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                ArticleItem item = getAdapter().getDataSetItem(i - 1);
                intent.putExtra(ArticleDetailFragment.ARTICLE_SID_KEY, item.getArticleId());
                intent.putExtra(ArticleDetailFragment.ARTICLE_TITLE_KEY, item.getArticleTitle());
                getActivity().startActivity(intent);
            }
        };
    }

    @Override
    public void loadData(boolean startup) {
        if (getIsCollection()) {
            // 收藏
            this.hasCached = false;
            List<ArticleItem> list = new ArrayList<>();
            try {
                list = MyApplication.getInstance().getDbUtils().findAll(ArticleItem.class);
            } catch (DbException ex) {
                CrashReport.postCatchedException(ex);
            }
            if (callback != null) {
                callback.onLoadFinish(40);
            }
            showToast(list == null ? 0 : list.size());
            getAdapter().setDataSet(list);
            getAdapter().notifyDataSetChanged();
        } else {
            ArrayList<ArticleItem> newsList = FileCacheKit.getInstance().getAsObject(getTypeKey().hashCode() + "", "list",
                    new TypeToken<ArrayList<ArticleItem>>() {
                    });
            if (newsList != null) {
                hasCached = true;
                topSid = newsList.get(1).getArticleId();
                getAdapter().setDataSet(newsList);
                getAdapter().notifyDataSetChanged();
            } else {
                this.hasCached = false;
            }
            this.current = 1;
        }
    }

    private void showToastAndCache(List<ArticleItem> itemList, int size) {
        if (size < 1) {
            Toolkit.showCrouton(getActivity(), getActivity().getString(R.string.message_no_new_news), CroutonStyle.CONFIRM);
        } else {
            Toolkit.showCrouton(getActivity(), getActivity().getString(R.string.message_new_news, size), CroutonStyle.INFO);
        }
        FileCacheKit.getInstance().putAsync(getTypeKey().hashCode() + "", Toolkit.getGson().toJson(itemList), "list", null);
    }

    private void showToast(int size) {
        if (size < 1) {
            Toolkit.showCrouton(getActivity(), getActivity().getString(R.string.message_no_new_news), CroutonStyle.CONFIRM);
        } else {
            Toolkit.showCrouton(getActivity(), getActivity().getString(R.string.message_new_news, size), CroutonStyle.INFO);
        }
    }

}
