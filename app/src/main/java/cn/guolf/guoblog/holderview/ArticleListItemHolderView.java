package cn.guolf.guoblog.holderview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.adapter.ArticleListAdapter;
import cn.guolf.guoblog.entity.ArticleItem;
import cn.guolf.guoblog.lib.kits.LogKits;

/**
 * Created by guolf on 7/17/15.
 */
public class ArticleListItemHolderView  extends MaterialRippleLayout {
    private TextView news_time;
    private TextView news_title;
    private TextView news_views;
    private TextView news_summary;
    private TextView news_comment;
    private ImageView news_image_large;
    private ImageView news_image_small;

    public ArticleListItemHolderView(Context context) {
        super(context);
    }

    public ArticleListItemHolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArticleListItemHolderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.news_time = (TextView) findViewById(R.id.news_time);
        this.news_title = (TextView) findViewById(R.id.news_title);
        this.news_views = (TextView) findViewById(R.id.news_views);
        this.news_summary = (TextView) findViewById(R.id.news_summary);
        this.news_comment = (TextView) findViewById(R.id.news_comments);
        this.news_image_large = (ImageView) findViewById(R.id.news_image_large);
        this.news_image_small = (ImageView) findViewById(R.id.news_image_small);
    }

    public void showNews(ArticleItem item, boolean showImage, boolean showLarge, DisplayImageOptions optionsLarge, DisplayImageOptions optionsSmall, ArticleListAdapter.AnimateFirstDisplayListener listener) {
        LogKits.i("showNews: "+item.getArticleTitle() );
        news_title.setText(item.getArticleTitle());
        news_views.setText("" + item.getReadTimes());
        news_time.setText(item.getPublishedTime());
       // news_comment.setText(item.getComments());
        news_summary.setText(item.getRemark());
//        if (!showImage) {
//            if (news_image_large.getVisibility() == VISIBLE) {
//                news_image_large.setVisibility(GONE);
//            }
//            if (news_image_small.getVisibility() == VISIBLE) {
//                news_image_small.setVisibility(GONE);
//            }
//        } else {
//            if (showLarge) {
//                if (news_image_small.getVisibility() == VISIBLE) {
//                    news_image_small.setVisibility(GONE);
//                }
//                if (item.getLargeImage() != null) {
//                    if (news_image_large.getVisibility() == GONE) {
//                        news_image_large.setVisibility(VISIBLE);
//                    }
//                    ImageLoader.getInstance().displayImage(item.getLargeImage(), news_image_large, optionsLarge, listener);
//                } else {
//                    if (news_image_large.getVisibility() == VISIBLE) {
//                        news_image_large.setVisibility(GONE);
//                    }
//                }
//            } else {
//                if (news_image_large.getVisibility() == VISIBLE) {
//                    news_image_large.setVisibility(GONE);
//                }
//                if (news_image_small.getVisibility() == GONE) {
//                    news_image_small.setVisibility(VISIBLE);
//                }
//                ImageLoader.getInstance().displayImage(item.getThumb(), news_image_small, optionsSmall);
//            }
//        }
    }
}

