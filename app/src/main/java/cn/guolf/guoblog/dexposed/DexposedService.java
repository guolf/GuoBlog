package cn.guolf.guoblog.dexposed;

import android.app.IntentService;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.patch.PatchMain;
import com.taobao.patch.PatchResult;
import com.tencent.bugly.crashreport.CrashReport;

import org.apache.http.Header;

import java.io.File;

import cn.guolf.guoblog.MyApplication;
import cn.guolf.guoblog.entity.HotPatch;
import cn.guolf.guoblog.lib.handler.GsonHttpResponseHandler;
import cn.guolf.guoblog.lib.kits.LogKits;
import cn.guolf.guoblog.lib.kits.NetKit;
import cn.guolf.guoblog.lib.kits.Toolkit;

/**
 * 1、判断当前机型是否支持dexposed，以及版本是否大于5.0
 * 2、判断当前版本是否存在patch
 * 3、判断patch是否已存在，不存在则下载patch文件
 * 4、判断patch与当前应用签名是否相同
 * 5、加载patch
 * json 格式
 * {"apkVersion":"1.0","flag":"1","patchUrl":"http://10.168.3.102/app-debug.apk"}
 */
public class DexposedService extends IntentService {

    public static final String ACTION_PATCH = "cn.guolf.guoblog.dexposed.action.PATCH";
    public static final String EXTRA_PARAM1 = "cn.guolf.guoblog.dexposed.extra.URL";
    private static final String urlPath = MyApplication.getInstance().getCacheDir().getAbsolutePath();
    private static String fileName = "";

    ResponseHandlerInterface handlerInterface = new GsonHttpResponseHandler<HotPatch>
            (new TypeToken<HotPatch>() {
            }) {

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            CrashReport.postCatchedException(throwable);
        }

        @Override
        protected void onError(int statusCode, Header[] headers, String responseString, Throwable cause) {
            CrashReport.postCatchedException(cause);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString, HotPatch patch) {
            if (patch.getFlag().equals("1")) {
                // 发现当前版本补丁包，判断是否已下载
                fileName = patch.getPatchUrl().substring(patch.getPatchUrl().lastIndexOf("/"));
                String fullPath = urlPath + fileName;
                File apk = new File(fullPath);
                LogKits.i(fullPath);
                if (apk.exists()) {
                    LogKits.i("文件已存在，go patch");
                    runPatch(fullPath);
                } else {
                    LogKits.i("文件不存在，go down");
                    NetKit.getInstance().downloadFile(patch.getPatchUrl(), new FileAsyncHttpResponseHandler(apk) {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                            CrashReport.postCatchedException(throwable);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, File file) {
                            runPatch(file.getAbsolutePath());
                        }
                    });
                }
            }
        }
    };
    private boolean isSupport = false;
    private boolean isLDevice = false;

    public DexposedService() {
        super("DexposedService");
    }

    private void runPatch(String path) {
        // 将apk与当前应用对比签名
        String sig1 = Toolkit.getApkSignature(path);
        String sig2 = Toolkit.getInstallPackageSignature(MyApplication.getInstance(), "cn.guolf.guoblog");
        LogKits.i("sig1:" + sig1 + "\nsig2:" + sig2);
        if (sig1.equals(sig2)) {
            PatchResult result = PatchMain.load(MyApplication.getInstance(), path, null);
            if (result.isSuccess()) {
                LogKits.i("patch success!");
            } else {
                CrashReport.postCatchedException(result.getThrowbale());
            }
        } else {
            LogKits.i("签名不符");
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PATCH.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleAction(param1);
            }
        }
    }

    private void handleAction(String param1) {
        // 判断是否支持dexposed
        isSupport = DexposedBridge.canDexposed(MyApplication.getInstance());
        isLDevice = android.os.Build.VERSION.SDK_INT >= 21;
        if (isSupport && !isLDevice) {
            NetKit.getInstance().getContentByUrl(param1, handlerInterface);
        } else {
            LogKits.i("not Support");
        }
    }

}
