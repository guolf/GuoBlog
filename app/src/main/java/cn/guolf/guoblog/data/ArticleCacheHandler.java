package cn.guolf.guoblog.data;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.data.impl.ArticleDetailProvider;
import cn.guolf.guoblog.entity.ArticleItem;
import cn.guolf.guoblog.lib.kits.FileCacheKit;
import cn.guolf.guoblog.lib.kits.LogKits;
import cn.guolf.guoblog.lib.kits.NetKit;
import cn.guolf.guoblog.lib.kits.PrefKit;

/**
 * Created by guolf on 7/17/15.
 * 文章缓存Handler
 */
public class ArticleCacheHandler extends Handler {

    private static final int MESSAGE_STOP = 3;
    private static final int MESSAGE_UPDATE_PROGRESS = 1;
    private static final int MESSAGE_FINISH_PROGRESS = 2;
    private int len;
    private int size = 0;
    private WeakReference<Context> context;
    private int failedCount = 0;
    private CacheThread thread;
    private int successCount = 0;
    private boolean start = false;
    private List<ArticleItem> mCacheList;
    private NotificationManager manager;
    private Notification.Builder builder;
    private String stringFormate = "成功 %d 条 失败 %d 条";
    private Bitmap largeLogo;


    public ArticleCacheHandler(Context context) {
        super(Looper.getMainLooper());
        this.context = new WeakReference<>(context);
        largeLogo = BitmapFactory.decodeResource(this.context.get().getResources(), R.mipmap.ic_launcher);
        init();
    }

    private void init() {
        manager = (NotificationManager) context.get().getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new Notification.Builder(context.get());
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        AtomicReference<String> info = new AtomicReference<>();
        switch (msg.what) {
            case MESSAGE_UPDATE_PROGRESS:
                info.set("正在缓存第 " + len + " 条文章");
                builder.setProgress(size, len, false);
                builder.setContentText(info.get());
                manager.notify(0, notificationCompt(builder));
                break;
            case MESSAGE_FINISH_PROGRESS:
                start = false;
                info.set(String.format(Locale.CHINA, stringFormate, successCount, failedCount));
                Toast.makeText(context.get(), info.get(), Toast.LENGTH_SHORT).show();
                manager.notify(0, notificationCompt(new Notification.Builder(context.get())
                        .setContentTitle("离线缓存已完成").setContentText(info.get()).setTicker("离线缓存已完成")
                        .setSmallIcon(R.mipmap.ic_logo).setLargeIcon(largeLogo)));
                break;
            case MESSAGE_STOP:
                start = false;
                info.set(String.format(Locale.CHINA, stringFormate, successCount, failedCount));
                Toast.makeText(context.get(), info.get(), Toast.LENGTH_SHORT).show();
                manager.notify(0, notificationCompt(new Notification.Builder(context.get())
                        .setContentTitle("离线缓存已取消").setContentText(info.get()).setTicker("离线缓存已取消")
                        .setSmallIcon(R.mipmap.ic_logo).setLargeIcon(largeLogo)));
                break;
        }
    }

    public void start() {
        if (!start) {
            this.start = true;
            builder.setProgress(0, 0, true);
            builder.setContentTitle("正在缓存文章中");
            builder.setContentText("请稍候");
            builder.setTicker("正在离线缓存文章");
            builder.setSmallIcon(R.mipmap.ic_logo);
            builder.setLargeIcon(largeLogo);
            builder.setOngoing(true);
            manager.notify(0, notificationCompt(builder));
            thread = new CacheThread("Cache Thread");
            thread.start();
        }
    }

    public void stop() {
        if (start) {
            thread.interrupt();
            sendEmptyMessage(MESSAGE_STOP);
        }
    }


    @SuppressLint("NewApi")
    private Notification notificationCompt(Notification.Builder builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return builder.build();
        } else {
            return builder.getNotification();
        }
    }

    public boolean isStart() {
        return start;
    }

    public void setCacheList(List<ArticleItem> cacheList) {
        if (!start) {
            if(cacheList.size()>40){
                mCacheList = cacheList.subList(0,40);
            }else {
                mCacheList = cacheList;
            }
            size = mCacheList.size();
        }
    }

    public void cleanNotification() {
        manager.cancel(0);
    }

    private class CacheThread extends Thread {
        private boolean cacheImage;
        public CacheThread(String s) {
            super(s);
            cacheImage = PrefKit.getBoolean(context.get(), R.string.pref_offline_image_key, false);
        }

        @Override
        public void run() {
            len = 0;
            successCount = 0;
            failedCount = 0;
            for (final ArticleItem item : mCacheList) {
                len++;
                post(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = MESSAGE_UPDATE_PROGRESS;
                        msg.obj = len;
                        sendMessage(msg);
                    }
                });
                if (FileCacheKit.getInstance().getAsObject(item.getArticleId(), ArticleItem.class) == null) {
                    NetKit.getInstance().getArticleDetailByUrlSync(item.getArticleId(), new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            failedCount++;
                            LogKits.d("", throwable.getCause());
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            successCount++;
                            ArticleDetailProvider.handleResponceString(item, responseString, true, cacheImage);
                        }

                        @Override
                        public void onProgress(int bytesWritten, int totalSize) {

                        }
                    });
                }
            }
            post(new Runnable() {
                @Override
                public void run() {
                    sendEmptyMessage(MESSAGE_FINISH_PROGRESS);
                }
            });
        }

    }
}
