package cn.guolf.guoblog.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guolf on 7/17/15.
 */
public class ArticleListObject {
    private List<ArticleItem> list = new ArrayList<ArticleItem>();

    public List<ArticleItem> getData() {
        return list;
    }

    public void setData(List<ArticleItem> data) {
        list = data;
    }

}
