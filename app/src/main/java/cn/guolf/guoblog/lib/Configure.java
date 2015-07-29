package cn.guolf.guoblog.lib;

import java.util.Locale;

/**
 * API配置
 */
public class Configure {

    public static final String BASE_URL = "http://www.guolingfa.cn/mobile/";
    public static final String LOGIN_RUL = BASE_URL + "LoginMobile";
    public static final String ARTICLE_LIST_URL = BASE_URL + "getArticleList";
    public static final String ARTICLE_DETAIL_URL = BASE_URL + "getArticleDetail";
    public static final String ARTICLE_DETAIL_BY_URL = "http://guolingfa.cn/Article/Details/%S";
    public static final String TALK_LIST_URL = BASE_URL + "getTalk";

    public static String buildArticleUrl(String sid) {
        return String.format(Locale.CHINA, ARTICLE_DETAIL_BY_URL, sid);
    }
}
