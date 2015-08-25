package cn.guolf.guoblog.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import cn.guolf.guoblog.BuildConfig;
import cn.guolf.guoblog.R;
import cn.guolf.guoblog.lib.ThemeManger;
import cn.guolf.guoblog.util.ShareUtils;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Author：guolf on 8/19/15 09:32
 * Email ：guo@guolingfa.cn
 */
public class AboutActivity extends AppCompatActivity implements SwipeBackActivityBase {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private SwipeBackActivityHelper mSwipeBackActivityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManger.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);

        // TODO: 8/19/15 bug,等待修复
        // mSwipeBackActivityHelper = new SwipeBackActivityHelper(this);
        // mSwipeBackActivityHelper.onActivityCreate();
        // setSwipeBackEnable(PrefKit.getBoolean(this, R.string.pref_swipeback_key, true));

        setContentView(R.layout.activity_about);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mCollapsingToolbarLayout.setTitle("关于GuoBlog");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.onBackPressed();
            }
        });

        ((TextView) findViewById(R.id.tvVersion)).setText("Version:" + BuildConfig.VERSION_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.menu_share:
                ShareUtils.share(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("AboutActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AboutActivity");
        MobclickAgent.onPause(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mSwipeBackActivityHelper.onPostCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackActivityHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
