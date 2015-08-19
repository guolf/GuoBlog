package cn.guolf.guoblog.util;

import android.content.Context;
import android.content.Intent;

import cn.guolf.guoblog.R;

/**
 * Author：guolf on 8/19/15 11:01
 * Email ：guo@guolingfa.cn
 */
public class ShareUtils {

    public static void share(Context context) {
        share(context, context.getString(R.string.share_text));
    }

    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.menu_share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.menu_share)));
    }
}
