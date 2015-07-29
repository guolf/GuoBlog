package cn.guolf.guoblog.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.entity.ArticleItem;
import cn.guolf.guoblog.fragments.ArticleDetailFragment;
import cn.guolf.guoblog.lib.kits.PrefKit;
import cn.guolf.guoblog.widget.FixViewPager;

/**
 * Created by guolf on 7/19/15.
 */
public class ArticleDetailActivity extends ExtendBaseActivity implements ArticleDetailFragment.NewsDetailCallBack {

    private List<Fragment> fragments = new ArrayList<>(2);
    private FixViewPager pager;
    private FragmentAdapter adapter;
    private String title;
    private boolean isNewDesignMode;
    private ViewGroup contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        isNewDesignMode = PrefKit.getBoolean(this, R.string.pref_new_detail_key, true);
        if (bundle != null && bundle.containsKey(ArticleDetailFragment.ARTICLE_SID_KEY) && bundle.containsKey(ArticleDetailFragment.ARTICLE_TITLE_KEY)) {
            title = bundle.getString(ArticleDetailFragment.ARTICLE_TITLE_KEY);
            setTitle("详情：" + title);
            contentView = (ViewGroup) findViewById(R.id.content);
            fragments.add(ArticleDetailFragment.getInstance(bundle.getString(ArticleDetailFragment.ARTICLE_SID_KEY) , title));
            if (isNewDesignMode) {
                setContentView(R.layout.pager_layout);
                pager = (FixViewPager) findViewById(R.id.pager);
                adapter = new FragmentAdapter(getSupportFragmentManager());
                pager.setAdapter(adapter);
                pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == 0) {
                            setTitle("详情：" + title);
                            setSwipeBackEnable(PrefKit.getBoolean(ArticleDetailActivity.this, R.string.pref_swipeback_key, true));
                        } else {
                            setTitle("评论：" + title);
                            setSwipeBackEnable(false);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragments.get(0)).commit();
            }
        } else {
            Toast.makeText(this, "缺少必要参数", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onArticleLoadFinish(ArticleItem item, boolean success) {

    }

    @Override
    public void onHideHtmlVideoView(View html5VideoView) {

    }

    @Override
    public void onShowHtmlVideoView(View html5VideoView) {

    }

    @Override
    public void onVideoFullScreen(boolean isFullScreen) {

    }

    @Override
    public void CommentAction(String sid, String title) {

    }

    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
