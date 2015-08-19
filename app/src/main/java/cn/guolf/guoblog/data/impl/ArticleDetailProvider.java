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

import cn.guolf.guoblog.MyApplication;
import cn.guolf.guoblog.data.BaseDataProvider;
import cn.guolf.guoblog.entity.ArticleItem;
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

    public static boolean handleResponceString(ArticleItem item,String resp,boolean shouldCache){
        return handleResponceString(item, resp,shouldCache,false);
    }

    public static boolean handleResponceString(ArticleItem item,String resp,boolean shouldCache,boolean cacheImage){
        Document doc = Jsoup.parse(resp);
        Elements newsHeadlines = doc.select(".blogs");
        //item.setFrom(newsHeadlines.select(".where").html());
        item.setPublishedTime(newsHeadlines.select(".d_time").html());

        Elements content = newsHeadlines.select(".infos");
        if(cacheImage){
            Elements images = content.select("img");
            for(Element image:images){
                Bitmap img = ImageLoader.getInstance().loadImageSync(image.attr("src"), MyApplication.getDefaultDisplayOption());
                if(img!=null) {
                    img.recycle();
                }
            }
        }
        item.setArticleContent(content.html());
        if(item.getArticleContent()!=null&&item.getArticleContent().length()>0){
            if(shouldCache) {
                FileCacheKit.getInstance().put(item.getArticleId(), Toolkit.getGson().toJson(item));
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void loadData(boolean startup) {

    }

    public void loadArticleAsyc(String sid) {
        NetKit.getInstance().getArticleDetailByUrl(sid, handler);
    }
}
