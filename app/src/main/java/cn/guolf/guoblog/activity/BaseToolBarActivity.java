package cn.guolf.guoblog.activity;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.lib.CroutonStyle;
import cn.guolf.guoblog.lib.ThemeManger;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * Created by guolf on 7/17/15.
 */

public abstract class BaseToolBarActivity extends AppCompatActivity {
    protected FrameLayout content;
    protected int colorPrimary;
    protected int colorPrimaryDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManger.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        super.setContentView(getBasicContentLayout());
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        content = (FrameLayout) findViewById(R.id.content);
        TypedArray array = obtainStyledAttributes(new int[]{R.attr.colorPrimary,R.attr.colorPrimaryDark,R.attr.colorAccent});
        colorPrimary = array.getColor(0, getResources().getColor(R.color.toolbarColor));
        colorPrimaryDark = array.getColor(1, getResources().getColor(R.color.statusColor));
        CroutonStyle.buildStyleInfo(colorPrimaryDark);
        CroutonStyle.buildStyleConfirm(array.getColor(2, Style.holoGreenLight));
        array.recycle();
    }

    @Override
    public void setContentView(int layoutResID) {
        content.removeAllViews();
        getLayoutInflater().inflate(layoutResID, content);
    }

    public void setContentViewSuper(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        content.removeAllViews();
        content.addView(view);
    }

    public void setContentViewUseSuper(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        content.removeAllViews();
        content.addView(view, params);
    }

    public void setContentViewsSuper(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // helper.notifyConfigureChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.clearCroutonsForActivity(this);
    }

    protected int getBasicContentLayout() {
        return R.layout.activity_basetoolbar;
    }
}
