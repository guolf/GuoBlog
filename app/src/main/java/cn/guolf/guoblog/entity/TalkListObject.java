package cn.guolf.guoblog.entity;

import java.util.List;

/**
 * Created by guolf on 7/23/15.
 */
public class TalkListObject {
    private List<TalkItem> list;

    public List<TalkItem> getData() {
        return list;
    }

    public void setData(List<TalkItem> data) {
        this.list = data;
    }
}
