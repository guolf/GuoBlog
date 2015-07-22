package cn.guolf.guoblog.fragments;

import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.data.impl.NetArticleListDataProvider;

/**
 * Created by guolf on 7/22/15.
 */
public class LifeArticleListFragment extends BaseArticleListFragment {


    @Override
    protected ListDataProvider getProvider() {
        return new NetArticleListDataProvider(getActivity()) {
            @Override
            public String getTypeKey() {
                return "life";
            }

            @Override
            public String getTypeName() {
                return "慢生活";
            }
        };
    }

    @Override
    public boolean hasMenu() {
        return true;
    }
}
