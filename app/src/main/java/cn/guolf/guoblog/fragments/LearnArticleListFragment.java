package cn.guolf.guoblog.fragments;

import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.data.impl.NetArticleListDataProvider;

/**
 * Created by guolf on 7/22/15.
 */
public class LearnArticleListFragment extends BaseArticleListFragment {


    @Override
    protected ListDataProvider getProvider() {
        return new NetArticleListDataProvider(getActivity()) {
            @Override
            public String getTypeKey() {
                return "learn";
            }

            @Override
            public String getTypeName() {
                return "学无止境";
            }
        };
    }

    @Override
    public boolean hasMenu() {
        return true;
    }
}
