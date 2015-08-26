package cn.guolf.guoblog;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.taobao.android.dexposed.DexposedBridge;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

import cn.guolf.guoblog.lib.MyCrashHandler;
import cn.guolf.guoblog.lib.database.DbUtils;
import cn.guolf.guoblog.lib.kits.FileCacheKit;
import cn.guolf.guoblog.lib.kits.PrefKit;


/**
 * Created by guolf on 7/17/15.
 */
public class MyApplication extends Application {

    public static DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
    private static MyApplication instance;
    private Boolean debug;
    private boolean listImageShowStatusChange;
    private DbUtils mDbUtils;

    public static MyApplication getInstance() {
        return instance;
    }

    public static DisplayImageOptions getDefaultDisplayOption() {
        return options;
    }

    public DbUtils getDbUtils() {
        return mDbUtils;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        debug = PrefKit.getBoolean(this, R.string.pref_debug_key, false);
        FileCacheKit.getInstance(this);
        MyCrashHandler.getInstance().init(this);
        initImageLoader(getApplicationContext());
        mDbUtils = DbUtils.create(this);
        //腾讯Bugly设置
        CrashReport.initCrashReport(this, "1103444121", false);
        // 友盟统计相关设置
        MobclickAgent.setCatchUncaughtExceptions(false);
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.openActivityDurationTrack(false);

        // dexposed
        DexposedBridge.canDexposed(this);
    }

    public void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO);
        if (debug) {
            builder.writeDebugLogs();
        }
        ImageLoaderConfiguration config = builder.build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public boolean getDebug() {
        return debug;
    }

    @Override
    public File getCacheDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return getExternalCacheDir();
        } else {
            return super.getCacheDir();
        }
    }

    public File getInternalCacheDir() {
        return super.getCacheDir();
    }

    public boolean isListImageShowStatusChange() {
        return listImageShowStatusChange;
    }

    public void setListImageShowStatusChange(boolean listImageShowStatusChange) {
        this.listImageShowStatusChange = listImageShowStatusChange;
    }
}
