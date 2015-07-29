package cn.guolf.guoblog.entity;

/**
 * Created by guolf on 7/22/15.
 */
public class TalkItem {

    private String Id;
    private String Content;
    private String Loaction;
    private String CreateTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getLoaction() {
        return Loaction;
    }

    public void setLoaction(String loaction) {
        Loaction = loaction;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
