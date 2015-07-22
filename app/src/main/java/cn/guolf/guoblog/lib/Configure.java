package cn.guolf.guoblog.lib;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * API配置
 */
public class Configure {

    public static final String BASE_URL = "http://www.guolingfa.cn/mobile/";
    public static final String LOGIN_RUL = BASE_URL + "LoginMobile";
    public static final String ARTICLE_LIST_URL = BASE_URL + "getArticleList";
    public static final String ARTICLE_DETAIL_URL = BASE_URL + "getArticleDetail";
    public static final String ARTICLE_DETAIL_BY_URL = "http://guolingfa.cn/Article/Details/%S";

    public static final String NEWS_LIST_URL = BASE_URL + "/more";

    private static final String ARTICLE_URL = BASE_URL + "/articles/%s.htm";
    public static final String TOPIC_NEWS_LIST = BASE_URL+"/topics/more";
    public static final String COMMENT_URL = BASE_URL + "/cmt";
    public static final String COMMENT_VIEW = BASE_URL + "/comment";
    public static final String SECOND_VIEW = BASE_URL + "/captcha.htm";
    public static final Pattern FAVOR_NEWS_TITLE = Pattern.compile("^(\\[|《|”)?((.)?)");
    public static final Pattern STANDRA_PATTERN = Pattern.compile("cnBeta\\.COM_中文业界资讯站");
    public static final Pattern SN_PATTERN = Pattern.compile("SN:\"(.{5})\"");
    public static final Pattern HOT_COMMENT_PATTERN = Pattern.compile("来自<strong>(.*)</strong>的(.*)对新闻:<a href=\"/articles/(.*).htm\" target=\"_blank\">(.*)</a>的评论");

    public static String buildArticleUrl(String sid) {
        return String.format(Locale.CHINA, ARTICLE_DETAIL_BY_URL, sid);
    }
}
