package cn.guolf.guoblog.fragments;

import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.data.impl.NetArticleListDataProvider;

/**
 * Created by guolf on 7/22/15.
 */
public class LifeArticleListFragment extends BaseArticleListFragment {


    public static LifeArticleListFragment newInstance(Boolean isCollection) {

        Bundle args = new Bundle();
        args.putBoolean("isCollection", isCollection);
        LifeArticleListFragment fragment = new LifeArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

            @Override
            public Boolean getIsCollection() {
                return getArguments() == null ? false : getArguments().getBoolean("isCollection", false);
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
        MobclickAgent.onPageStart("LifeArticleListFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LifeArticleListFragment");
    }
}
