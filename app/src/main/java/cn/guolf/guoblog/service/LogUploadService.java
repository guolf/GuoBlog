package cn.guolf.guoblog.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cn.guolf.guoblog.AppConfig;
import cn.guolf.guoblog.util.StringUtils;

/**
 * Author：guolf on 8/7/15 14:58
 * Email ：guo@guolingfa.cn
 * 日志上传服务
 */
public class LogUploadService extends Service {
    public LogUploadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final File log = new File(AppConfig.DEFAULT_LOG_FILE_PATH);
        String data = null;
        try {
            if (log.isDirectory()) {
                for (File file : log.listFiles()) {
                    FileInputStream inputStream = new FileInputStream(file);
                    data += StringUtils.toConvertString(inputStream);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(data)) {
            // TODO upload log,if success then delete log files
        } else {
            LogUploadService.this.stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
