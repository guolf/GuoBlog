package cn.guolf.guoblog.processers;

import android.view.View;

import cn.guolf.guoblog.data.impl.TalkListDataProvider;
import cn.guolf.guoblog.entity.TalkItem;

/**
 * Created by guolf on 7/23/15.
 */
public class TalkListProcesser extends BaseListProcesser<TalkItem, TalkListDataProvider> {

    public TalkListProcesser(TalkListDataProvider dataProvider) {
        super(dataProvider);
    }

    @Override
    public void assumeView(View view) {
        super.assumeView(view);
    }
}
