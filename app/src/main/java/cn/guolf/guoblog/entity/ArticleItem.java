package cn.guolf.guoblog.entity;

import cn.guolf.guoblog.lib.database.annotation.Id;
import cn.guolf.guoblog.lib.database.annotation.Table;

/**
 * Created by guolf on 7/17/15.
 */
@Table(name = "Article")
public class ArticleItem {

    @Id
    private String ArticleId;
    private String ArticleTitle;
    private String ArticleContent;
    private String PublishedTime;
    private String Remark;
    private int ReadTimes;

    public ArticleItem() {
    }

    public ArticleItem(String id, String title) {
        this.ArticleId = id;
        this.ArticleTitle = title;
    }

    public int getReadTimes() {
        return ReadTimes;
    }

    public void setReadTimes(int readTimes) {
        ReadTimes = readTimes;
    }

    public String getArticleId() {
        return ArticleId;
    }

    public void setArticleId(String articleId) {
        ArticleId = articleId;
    }

    public String getArticleTitle() {
        return ArticleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        ArticleTitle = articleTitle;
    }

    public String getArticleContent() {
        return ArticleContent;
    }

    public void setArticleContent(String articleContent) {
        ArticleContent = articleContent;
    }

    public String getPublishedTime() {
        return PublishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        PublishedTime = publishedTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

}
