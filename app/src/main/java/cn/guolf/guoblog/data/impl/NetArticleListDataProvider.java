package cn.guolf.guoblog.data.impl;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.ArrayList;
import java.util.List;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.activity.ArticleDetailActivity;
import cn.guolf.guoblog.adapter.ArticleListAdapter;
import cn.guolf.guoblog.entity.ArticleItem;
import cn.guolf.guoblog.entity.ArticleListObject;
import cn.guolf.guoblog.entity.ResponseObject;
import cn.guolf.guoblog.fragments.ArticleDetailFragment;
import cn.guolf.guoblog.lib.CroutonStyle;
import cn.guolf.guoblog.lib.handler.BaseHttpResponseHandler;
import cn.guolf.guoblog.lib.kits.FileCacheKit;
import cn.guolf.guoblog.lib.kits.NetKit;
import cn.guolf.guoblog.lib.kits.Toolkit;

/**
 * Created by guolf on 7/17/15.
 */
public abstract class NetArticleListDataProvider extends  BaseArticleListDataProvider<ArticleListAdapter> {

    private String topSid;
    private int current;

    private ResponseHandlerInterface newsPage = new BaseHttpResponseHandler<ArticleListObject>(
            new TypeToken<ResponseObject<ArticleListObject>>() {
            }){

        @Override
        protected void onSuccess(ArticleListObject result) {
            List<ArticleItem> itemList = result.getData();
            List<ArticleItem> dataSet = getAdapter().getDataSet();
            int size = 0;
            boolean find = false;
//            for (int i = 0; i < itemList.size(); i++) {
//                ArticleItem item = itemList.get(i);
//                if (itemList.get(i).getCounter() != null && item.getComments() != null) {
//                    int num = Integer.parseInt(item.getCounter());
//                    if (num > 9999) {
//                        item.setCounter("9999+");
//                    }
//                    num = Integer.parseInt(item.getComments());
//                    if (num > 999) {
//                        item.setComments("999+");
//                    }
//                } else {
//                    item.setCounter("0");
//                    item.setComments("0");
//                }
//                StringBuilder sb = new StringBuilder(Html.fromHtml(item.getHometext().replaceAll("<.*?>|[\\r|\\n]", "")));
//                if (sb.length() > 140) {
//                    item.setSummary(sb.replace(140, sb.length(), "...").toString());
//                } else {
//                    item.setSummary(sb.toString());
//                }
//                if (item.getThumb().contains("thumb")) {
//                    item.setLargeImage(item.getThumb().replaceAll("(\\.\\w{3,4})?_100x100|thumb/mini/", ""));
//                }
//                if (!find && item.getArticleId() != topSid) {
//                    size++;
//                } else if (!find) {
//                    find = true;
//                }
//            }
            if (!find) {
                size++;
            }
            dataSet.addAll(itemList);
//            if (!hasCached || result.getPage() == 1) {
//                hasCached = true;
//                getAdapter().setDataSet(itemList);
//                if(itemList.size()>2) {
//                    topSid = itemList.get(1).getArticleId();
//                }
//                showToastAndCache(itemList, size - 1);
//            } else {
//                dataSet.addAll(itemList);
//            }
//            current = result.getPage();
        }

        @Override
        protected Activity getActivity() {
            return NetArticleListDataProvider.this.getActivity();
        }

        @Override
        public void onFinish() {
            if(callback!=null){
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
        makeRequest(1, getTypeKey(), newsPage);
    }

    @Override
    public void loadNextData() {
        makeRequest(current + 1, getTypeKey(), newsPage);
    }

    public void makeRequest(int page, String type, ResponseHandlerInterface handlerInterface){
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
                intent.putExtra(ArticleDetailFragment.ARTICLE_TITLE_KEY,item.getArticleTitle());
                getActivity().startActivity(intent);
            }
        };
    }

    @Override
    public void loadData(boolean startup) {
        ArrayList<ArticleItem> newsList = FileCacheKit.getInstance().getAsObject(getTypeKey().hashCode() + "", "list", new TypeToken<ArrayList<ArticleItem>>() {
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

    private void showToastAndCache(List<ArticleItem> itemList, int size) {
        if (size < 1) {
            Toolkit.showCrouton(getActivity(), getActivity().getString(R.string.message_no_new_news), CroutonStyle.CONFIRM);
        } else {
            Toolkit.showCrouton(getActivity(), getActivity().getString(R.string.message_new_news, size), CroutonStyle.INFO);
        }
        FileCacheKit.getInstance().putAsync(getTypeKey().hashCode() + "", Toolkit.getGson().toJson(itemList), "list", null);
    }
}
