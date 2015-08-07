package cn.guolf.guoblog;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Author：guolf on 8/7/15 14:58
 * Email ：guo@guolingfa.cn
 */
public class AppConfig {
    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "GuoBlog"
            + File.separator + "img" + File.separator;
    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "GuoBlog"
            + File.separator + "download" + File.separator;
    // 默认存放日志的路径
    public final static String DEFAULT_LOG_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "GuoBlog"
            + File.separator + "logs" + File.separator;
    private static AppConfig appConfig;
    private Context mContext;

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }

}
