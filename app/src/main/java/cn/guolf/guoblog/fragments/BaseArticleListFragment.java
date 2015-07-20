package cn.guolf.guoblog.fragments;

import cn.guolf.guoblog.adapter.BaseAdapter;
import cn.guolf.guoblog.data.impl.BaseArticleListDataProvider;
import cn.guolf.guoblog.entity.ArticleItem;
import cn.guolf.guoblog.processers.ArticleListProcesser;

/**
 * Created by guolf on 7/17/15.
 */
public abstract class BaseArticleListFragment<Adapter extends BaseAdapter<ArticleItem>,Provider
        extends BaseArticleListDataProvider<Adapter>>
        extends BaseListFragment<ArticleItem,Adapter,Provider,ArticleListProcesser<Provider>> {
    @Override
    protected ArticleListProcesser<Provider> createProcesser(Provider provider) {
        return new ArticleListProcesser<>(provider);
    }
}
