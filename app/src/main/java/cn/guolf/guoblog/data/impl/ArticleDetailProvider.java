package cn.guolf.guoblog.data.impl;

import android.app.Activity;
import android.graphics.Bitmap;

import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;

import cn.guolf.guoblog.MyApplication;
import cn.guolf.guoblog.data.BaseDataProvider;
import cn.guolf.guoblog.entity.ArticleItem;
import cn.guolf.guoblog.lib.Configure;
import cn.guolf.guoblog.lib.kits.FileCacheKit;
import cn.guolf.guoblog.lib.kits.NetKit;
import cn.guolf.guoblog.lib.kits.Toolkit;

/**
 * Created by guolf on 7/19/15.
 */
public class ArticleDetailProvider extends BaseDataProvider<String> {

    private TextHttpResponseHandler handler =  new TextHttpResponseHandler() {

        @Override
        public void onStart() {
            if(callback!=null) {
                callback.onLoadStart();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            if(callback!=null) {
                callback.onLoadFailure();
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            if(callback!=null){
                callback.onLoadSuccess(responseString);
            }
        }
    };

    public ArticleDetailProvider(Activity activity){
        super(activity);
    }

    @Override
    public void loadData(boolean startup) {

    }

    public void loadArticleAsyc(String sid){
        NetKit.getInstance().getNewsBySid(sid, handler);
    }

    public static boolean handleResponceString(ArticleItem item,String resp,boolean shouldCache){
        return handleResponceString(item, resp,shouldCache,false);
    }

    public static boolean handleResponceString(ArticleItem item,String resp,boolean shouldCache,boolean cacheImage){
//        Document doc = Jsoup.parse(resp);
//        Elements newsHeadlines = doc.select(".body");
//        item.setFrom(newsHeadlines.select(".where").html());
//        item.setInputtime(newsHeadlines.select(".date").html());
//        Elements introduce = newsHeadlines.select(".introduction");
//        introduce.select("div").remove();
//        item.setHometext(introduce.html());
//        Elements content = newsHeadlines.select(".content");
//        if(cacheImage){
//            Elements images = content.select("img");
//            for(Element image:images){
//                Bitmap img = ImageLoader.getInstance().loadImageSync(image.attr("src"), MyApplication.getDefaultDisplayOption());
//                if(img!=null) {
//                    img.recycle();
//                }
//            }
//        }
//        item.setContent(content.html());
//        Matcher snMatcher = Configure.SN_PATTERN.matcher(resp);
//        if (snMatcher.find())
//            item.setSN(snMatcher.group(1));
//        if(item.getContent()!=null&&item.getContent().length()>0){
//            if(shouldCache) {
//                FileCacheKit.getInstance().put(item.getArticleId() + "", Toolkit.getGson().toJson(item));
//            }
//            return true;
//        }else{
//            return false;
//        }
        return false;
    }
}
