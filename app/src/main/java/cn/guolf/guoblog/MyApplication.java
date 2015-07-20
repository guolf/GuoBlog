package cn.guolf.guoblog;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

import cn.guolf.guoblog.lib.Emoticons;
import cn.guolf.guoblog.lib.MyCrashHandler;
import cn.guolf.guoblog.lib.database.DbUtils;
import cn.guolf.guoblog.lib.kits.FileCacheKit;
import cn.guolf.guoblog.lib.kits.PrefKit;

/**
 * Created by guolf on 7/17/15.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    private Boolean debug;
    private boolean listImageShowStatusChange;

    public DbUtils getDbUtils() {
        return mDbUtils;
    }

    private DbUtils mDbUtils;

    public static DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();

    @Override
    public void onCreate() {
        instance = this;
        debug = PrefKit.getBoolean(this, R.string.pref_debug_key, false);
        FileCacheKit.getInstance(this);
        MyCrashHandler.getInstance().init(this);
        initImageLoader(getApplicationContext());
        //Emoticons.init(this);
        mDbUtils = DbUtils.create(this);
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

    public static MyApplication getInstance() {
        return instance;
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

    public static DisplayImageOptions getDefaultDisplayOption() {
        return options;
    }

    public boolean isListImageShowStatusChange() {
        return listImageShowStatusChange;
    }

    public void setListImageShowStatusChange(boolean listImageShowStatusChange) {
        this.listImageShowStatusChange = listImageShowStatusChange;
    }
}
