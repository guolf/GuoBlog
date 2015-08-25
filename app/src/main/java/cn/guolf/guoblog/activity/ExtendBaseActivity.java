package cn.guolf.guoblog.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.lib.kits.PrefKit;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by guolf on 7/17/15.
 */
public class ExtendBaseActivity extends BaseToolBarActivity implements SwipeBackActivityBase {

    private SwipeBackActivityHelper mSwipeBackActivityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        mSwipeBackActivityHelper = new SwipeBackActivityHelper(this);
        mSwipeBackActivityHelper.onActivityCreate();
        setSwipeBackEnable(PrefKit.getBoolean(this, R.string.pref_swipeback_key, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackActivityHelper.getSwipeBackLayout();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackActivityHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mSwipeBackActivityHelper != null)
            return mSwipeBackActivityHelper.findViewById(id);
        return v;
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
