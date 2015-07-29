package cn.guolf.guoblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.entity.TalkItem;

/**
 * Created by guolf on 7/22/15.
 * 碎言碎语-适配器
 */
public class TalkListAdapter extends BaseAdapter<TalkItem> {

    public TalkListAdapter(Context context, List<TalkItem> items) {
        super(context, items);
    }

    @Override
    protected View bindViewAndData(LayoutInflater infater, int position, View convertView, ViewGroup parent) {
        View view = infater.inflate(R.layout.talk_item, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.content);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TalkItem item = getDataSetItem(position);
        textView.setText(item.getContent());
        tvDate.setText(item.getCreateTime());
        return view;
    }
}
