package cn.guolf.guoblog.data.impl;

import android.app.Activity;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.ArrayList;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.adapter.TalkListAdapter;
import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.entity.ResponseObject;
import cn.guolf.guoblog.entity.TalkItem;
import cn.guolf.guoblog.entity.TalkListObject;
import cn.guolf.guoblog.lib.CroutonStyle;
import cn.guolf.guoblog.lib.handler.BaseHttpResponseHandler;
import cn.guolf.guoblog.lib.kits.NetKit;
import cn.guolf.guoblog.lib.kits.Toolkit;

/**
 * Created by guolf on 7/22/15.
 * 碎言碎语 - 数据访问
 */
public class TalkListDataProvider extends ListDataProvider<TalkItem, TalkListAdapter> {

    private int current;
    private ResponseHandlerInterface talkPage = new BaseHttpResponseHandler<TalkListObject>(new TypeToken<ResponseObject<TalkListObject>>() {
    }) {

        @Override
        protected void onSuccess(TalkListObject result) {

            if (current == 1) {
                getAdapter().setDataSet(result.getData());
                Toolkit.showCrouton(getActivity(), getActivity().getString(R.string.message_flush_success), CroutonStyle.INFO);
                //FileCacheKit.getInstance().putAsync(getTypeKey().hashCode() + "", Toolkit.getGson().toJson(result), "list", null);
            } else {
                getAdapter().getDataSet().addAll(result.getData());
            }
        }

        @Override
        protected Activity getActivity() {
            return TalkListDataProvider.this.getActivity();
        }

        @Override
        public void onFinish() {
            if (callback != null) {
                callback.onLoadFinish(10);
            }
        }
    };

    public TalkListDataProvider(Activity activity) {
        super(activity);
        hasCached = false;
    }

    @Override
    public void loadData(boolean startup) {

    }

    @Override
    public void loadNextData() {
        current++;
        NetKit.getInstance().getTalkListByPage(current, talkPage);
    }

    @Override
    public void loadNewData() {
        current = 1;
        NetKit.getInstance().getTalkListByPage(current, talkPage);
    }

    @Override
    protected TalkListAdapter newAdapter() {
        return new TalkListAdapter(getActivity(), new ArrayList<TalkItem>());
    }

    @Override
    public String getTypeName() {
        return "碎言碎语";
    }

    @Override
    public String getTypeKey() {
        return "talk";
    }


}
