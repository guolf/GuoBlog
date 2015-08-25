package cn.guolf.guoblog.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.data.impl.ArticleDetailProvider;
import cn.guolf.guoblog.entity.ArticleItem;
import cn.guolf.guoblog.processers.ArticleDetailProcesser;

/**
 * Created by guolf on 7/19/15.
 */
public class ArticleDetailFragment extends Fragment{
    public static final String ARTICLE_SID_KEY = "key_article_sid";
    public static final String ARTICLE_TITLE_KEY = "key_article_title";
    private ArticleDetailProcesser processer;
    private String sid;
    private String title = "";

    public static ArticleDetailFragment getInstance(String sid,String title){
        ArticleDetailFragment f = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARTICLE_SID_KEY, sid);
        args.putString(ARTICLE_TITLE_KEY, title);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if(args!=null&&args.containsKey(ARTICLE_SID_KEY)&&args.containsKey(ARTICLE_TITLE_KEY)){
            sid = args.getString(ARTICLE_SID_KEY);
            title = args.getString(ARTICLE_TITLE_KEY);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(processer==null) {
            processer = new ArticleDetailProcesser(new ArticleDetailProvider(activity));
        }
        processer.setActivity((AppCompatActivity) activity);
        if(activity instanceof NewsDetailCallBack){
            processer.setCallBack((NewsDetailCallBack) activity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_article_detail,container,false);
        processer.assumeView(view);
        processer.setArticleItem(sid, title);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        processer.loadData(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        processer.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return processer.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        processer.onResume();
        MobclickAgent.onPageStart("ArticleDetailFragment.".concat(title));
    }

    @Override
    public void onPause() {
        super.onStop();
        processer.onPause();
        MobclickAgent.onPageEnd("ArticleDetailFragment.".concat(title));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        processer.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        processer.onConfigurationChanged(newConfig);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        return processer.onKeyDown(keyCode, event);
    }

    public interface NewsDetailCallBack {
        void onArticleLoadFinish(ArticleItem item, boolean success);

        void CommentAction(String sid, String title);

        void onVideoFullScreen(boolean isFullScreen);

        void onShowHtmlVideoView(View html5VideoView);

        void onHideHtmlVideoView(View html5VideoView);
    }

}
