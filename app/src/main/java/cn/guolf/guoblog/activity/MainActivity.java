package cn.guolf.guoblog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.dexposed.DownloadService;
import cn.guolf.guoblog.fragments.NavigationDrawerFragment;
import cn.guolf.guoblog.fragments.UpdateFragment;
import cn.guolf.guoblog.lib.kits.PrefKit;
import de.keyboardsurfer.android.widget.crouton.Crouton;


public class MainActivity extends BaseToolBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public boolean changeTheme;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private int current = -1;
    private long lastpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        changeTheme = false;
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.update(this);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes:
                        if (UmengUpdateAgent.isIgnore(MainActivity.this, updateInfo)) {
                            // 用户忽略更新该版本
                        } else {
                            UpdateFragment.newInstance(updateInfo).show(getFragmentManager(), "udpate");
                        }
                        break;
                    case UpdateStatus.No:
                        if (PrefKit.getBoolean(MainActivity.this, "checkUpdate", false)) {
                            PrefKit.writeBoolean(MainActivity.this, "checkUpdate", false);
                            Toast.makeText(MainActivity.this, "未发现新版本", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
        runPatch();
    }

    private void runPatch() {
        Intent intent = new Intent();
        intent.setAction(DownloadService.ACTION_PATCH);
        intent.putExtra(DownloadService.EXTRA_PARAM1, "http://10.168.3.102/patch.json");
        startService(intent);
    }

    @Override
    public void onNavigationDrawerItemSelected(Fragment fragment, int pos) {
        if (fragment != null && current != pos) {
            Crouton.clearCroutonsForActivity(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            current = pos;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mNavigationDrawerFragment.isDrawerOpen()) {
                mNavigationDrawerFragment.closeDrawer();
                return true;
            } else if (current != 0) {
                mNavigationDrawerFragment.onBackPassed();
                return true;
            }
            if (System.currentTimeMillis() - lastpress < 1000) {
                this.finish();
            } else {
                lastpress = System.currentTimeMillis();
                Toast.makeText(this, "再按一次返回退出程序", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getBasicContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!changeTheme) {
            this.finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainActivity");
        MobclickAgent.onPause(this);
    }
}
