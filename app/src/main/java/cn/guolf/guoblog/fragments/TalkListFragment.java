package cn.guolf.guoblog.fragments;

import cn.guolf.guoblog.adapter.TalkListAdapter;
import cn.guolf.guoblog.data.impl.TalkListDataProvider;
import cn.guolf.guoblog.entity.TalkItem;
import cn.guolf.guoblog.processers.BaseListProcesser;
import cn.guolf.guoblog.processers.TalkListProcesser;

/**
 * Created by guolf on 7/22/15.
 */
public class TalkListFragment extends BaseListFragment<TalkItem, TalkListAdapter, TalkListDataProvider, BaseListProcesser<TalkItem, TalkListDataProvider>> {

    @Override
    protected TalkListDataProvider getProvider() {
        return new TalkListDataProvider(getActivity());
    }

    @Override
    protected BaseListProcesser<TalkItem, TalkListDataProvider> createProcesser(TalkListDataProvider provider) {
        return new TalkListProcesser(provider);
    }
}
