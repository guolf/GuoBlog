package cn.guolf.guoblog.lib.kits;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicHeader;
import org.jsoup.helper.StringUtil;

import java.util.List;

import cn.guolf.guoblog.MyApplication;
import cn.guolf.guoblog.lib.Configure;


public class NetKit {

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";
    private static NetKit instance;
    private AsyncHttpClient mClient;
    private SyncHttpClient mSyncHttpClient;

    private NetKit() {
        mClient = new AsyncHttpClient();
        mClient.setCookieStore(new BasicCookieStore());
        mClient.setConnectTimeout(3000);
        mClient.setResponseTimeout(6000);
        mClient.setMaxRetriesAndTimeout(3, 200);
        mClient.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.45 Safari/537.36");
        mSyncHttpClient = new SyncHttpClient();
    }

    public static int getConnectedType() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
            return mNetworkInfo.getType();
        }
        return -1;
    }

    public static NetKit getInstance() {
        if (instance == null) {
            instance = new NetKit();
        }
        return instance;
    }

    // Origin 只用于post请求，Referer 用于所有请求，都表示请求来源
    public static Header[] getAuthHeader() {
        return new Header[]{
                new BasicHeader("Referer", "http://www.guolingfa.cn/"),
                new BasicHeader("Origin", "http://www.guolingfa.cn"),
                new BasicHeader("X-Requested-With", "XMLHttpRequest")
        };
    }

    public static boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isMobileConnected() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    // 获取文章列表
    public void getArticlelistByPage(int page, String type, ResponseHandlerInterface handlerInterface) {
        RequestParams params = new RequestParams();
        params.add("type", type);
        params.add("cpage", page + "");
        params.add("pagesize","10");
        params.add("_", System.currentTimeMillis() + "");
        mClient.get(null, Configure.ARTICLE_LIST_URL, getAuthHeader(), params, handlerInterface);
    }

    /**
     * 获取文章评论数
     */
    public void getArticleCommentsCountByIds(List<String> ids, ResponseHandlerInterface handlerInterface) {

        mClient.get(null, String.format(Configure.ARTICLE_LIST_URL, StringUtil.join(ids, ",")), getAuthHeader(), null, handlerInterface);
    }

    // 获取文章详情
    public void getArticleDetailById(String id,ResponseHandlerInterface handlerInterface){
        LogKits.i("getArticleDetailById:" + id);
        RequestParams params = new RequestParams();
        params.add("id",id);
        mClient.get(null,Configure.ARTICLE_DETAIL_URL,getAuthHeader(),params,handlerInterface);
    }

    public void getArticleDetailByUrl(String id,ResponseHandlerInterface handlerInterface){
        mClient.get(Configure.buildArticleUrl(id),handlerInterface);
    }

    public void getContentByUrl(String url, ResponseHandlerInterface handlerInterface) {
        mClient.get(url, handlerInterface);
    }

    public void getArticleDetailByUrlSync(String id, ResponseHandlerInterface handlerInterface) {
        mSyncHttpClient.get(Configure.buildArticleUrl(id), handlerInterface);
    }

    public void getTalkListByPage(int page, ResponseHandlerInterface responseHandlerInterface) {
        LogKits.i("getTalkListByPage");
        RequestParams params = new RequestParams();
        params.add("cpage", page + "");
        params.add("pagesize", "10");
        params.add("_", System.currentTimeMillis() + "");
        mClient.get(null, Configure.TALK_LIST_URL, getAuthHeader(), params, responseHandlerInterface);
    }

    public void downloadFile(String url, ResponseHandlerInterface handler) {
        mClient.get(url, handler);
    }


    public AsyncHttpClient getClient() {
        return mClient;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
