package cn.guolf.guoblog.fragments;

import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.data.impl.NetArticleListDataProvider;
import cn.guolf.guoblog.lib.kits.LogKits;

/**
 * Created by guolf on 7/22/15.
 */
public class LearnArticleListFragment extends BaseArticleListFragment {


    public static LearnArticleListFragment newInstance(Boolean isCollection) {

        Bundle args = new Bundle();
        args.putBoolean("isCollection", isCollection);
        LearnArticleListFragment fragment = new LearnArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogKits.i("LearnArticleListFragment onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LearnArticleListFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LearnArticleListFragment");
    }
}
