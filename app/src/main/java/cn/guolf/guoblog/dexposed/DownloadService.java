package cn.guolf.guoblog.dexposed;

import android.app.IntentService;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.patch.PatchMain;
import com.taobao.patch.PatchResult;

import org.apache.http.Header;

import java.io.File;

import cn.guolf.guoblog.MyApplication;
import cn.guolf.guoblog.entity.HotPatch;
import cn.guolf.guoblog.lib.handler.GsonHttpResponseHandler;
import cn.guolf.guoblog.lib.kits.LogKits;
import cn.guolf.guoblog.lib.kits.NetKit;
import cn.guolf.guoblog.lib.kits.Toolkit;


public class DownloadService extends IntentService {

    public static final String ACTION_PATCH = "cn.guolf.guoblog.dexposed.action.PATCH";
    public static final String EXTRA_PARAM1 = "cn.guolf.guoblog.dexposed.extra.URL";
    FileAsyncHttpResponseHandler fileAsyncHttpResponseHandler = new FileAsyncHttpResponseHandler(MyApplication.getInstance()) {

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
            LogKits.e(throwable.getMessage());
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, File file) {
            // 下载成功，将apk与当前应用对比签名
            LogKits.i("file:" + file.getAbsolutePath());
            String sig1 = Toolkit.getApkSignature(file.getAbsolutePath());
            String sig2 = Toolkit.getInstallPackageSignature(MyApplication.getInstance(), "cn.guolf.guoblog");
            LogKits.i("sig1:" + sig1 + "\nsig2:" + sig2);
            if (sig1.equals(sig2)) {
                LogKits.i("签名符合");
                PatchResult result = PatchMain.load(MyApplication.getInstance(), file.getAbsolutePath(), null);
                if (result.isSuccess()) {
                    LogKits.e("patch success!");
                } else {
                    LogKits.e("patch error is " + result.getErrorInfo());
                }
            } else {
                LogKits.i("签名不符");
            }
        }
    };
    ResponseHandlerInterface handlerInterface = new GsonHttpResponseHandler<HotPatch>
            (new TypeToken<HotPatch>() {
            }) {

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            //CrashReport.postCatchedException(throwable);
            LogKits.i(responseString);
        }

        @Override
        protected void onError(int statusCode, Header[] headers, String responseString, Throwable cause) {
            //CrashReport.postCatchedException(cause);
            LogKits.i(responseString);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString, HotPatch patch) {
            LogKits.i("onSuccess:" + patch.getPatchUrl());
            if (patch.getFlag().equals("1")) {
                // 存在补丁包，下载
                NetKit.getInstance().downloadFile(patch.getPatchUrl(), fileAsyncHttpResponseHandler);
            }
        }
    };
    private boolean isSupport = false;
    private boolean isLDevice = false;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PATCH.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionFoo(param1);
            }
        }
    }

    private void handleActionFoo(String param1) {
        LogKits.i("param1:" + param1);
        isSupport = DexposedBridge.canDexposed(MyApplication.getInstance());
        isLDevice = android.os.Build.VERSION.SDK_INT >= 21;
//        if (isSupport && !isLDevice) {
        NetKit.getInstance().getContentByUrl(param1, handlerInterface);
//        } else {
//            LogKits.i("not Support");
//        }
    }

}
