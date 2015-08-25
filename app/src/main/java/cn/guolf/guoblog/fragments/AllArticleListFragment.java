package cn.guolf.guoblog.fragments;

import com.umeng.analytics.MobclickAgent;

import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.data.impl.NetArticleListDataProvider;

/**
 * Created by guolf on 7/17/15.
 */
public class AllArticleListFragment extends BaseArticleListFragment {


    @Override
    protected ListDataProvider getProvider() {
        return new NetArticleListDataProvider(getActivity()) {
            @Override
            public String getTypeKey() {
                return "all";
            }

            @Override
            public String getTypeName() {
                return "全部文章";
            }

            @Override
            public Boolean getIsCollection() {
                return false;
            }
        };
    }

    @Override
    public boolean hasMenu() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("AllArticleListFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AllArticleListFragment");
    }
}
