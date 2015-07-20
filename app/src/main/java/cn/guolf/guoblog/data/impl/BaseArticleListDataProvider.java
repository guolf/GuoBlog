package cn.guolf.guoblog.data.impl;

import android.app.Activity;

import cn.guolf.guoblog.adapter.BaseAdapter;
import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.entity.ArticleItem;

/**
 * Created by guolf on 7/17/15.
 */
public abstract class BaseArticleListDataProvider <Adapter extends BaseAdapter<ArticleItem>> extends ListDataProvider<ArticleItem,Adapter> {
    public BaseArticleListDataProvider(Activity activity) {
        super(activity);
    }
}
