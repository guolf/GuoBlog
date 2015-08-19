package cn.guolf.guoblog.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.widget.SlidingTabLayout.SlidingTabLayout;

/**
 * Author：guolf on 8/18/15 10:32
 * Email ：guo@guolingfa.cn
 * 我的收藏
 */
public class CollectionFragment extends Fragment {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager mPager;
    private String[] titles = {"学无止境", "慢生活"};
    private Fragment[] fragments = {LearnArticleListFragment.newInstance(true), LifeArticleListFragment.newInstance(true)};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        this.mPager = (ViewPager) view.findViewById(R.id.pager);
        this.slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        this.slidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        TypedArray array = getActivity().getTheme().obtainStyledAttributes(new int[]{R.attr.colorPrimaryDark});
        this.slidingTabLayout.setSelectedIndicatorColors(array.getColor(0, getResources().getColor(R.color.statusColor)));
        array.recycle();
        this.slidingTabLayout.setDistributeEvenly(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPager.setAdapter(new NavigationAdapter(getChildFragmentManager()));
        this.mPager.requestDisallowInterceptTouchEvent(true);
        this.slidingTabLayout.setViewPager(mPager);
    }

    private class NavigationAdapter extends FragmentPagerAdapter {

        public NavigationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
