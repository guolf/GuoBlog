package cn.guolf.guoblog.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateResponse;

import java.io.File;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.lib.kits.UIKit;

/**
 * Author：guolf on 8/20/15 08:55
 * Email ：guo@guolingfa.cn
 */
public class UpdateFragment extends DialogFragment implements View.OnClickListener {

    private Button mUpdate, mCancel;
    private TextView mContent;
    private CheckBox mIgnore;
    private UpdateResponse mResponse;

    public static UpdateFragment newInstance(UpdateResponse response) {

        Bundle args = new Bundle();
        args.putSerializable("updateinfo", response);
        UpdateFragment fragment = new UpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            this.mResponse = (UpdateResponse) getArguments().getSerializable("updateinfo");
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        if (width > UIKit.dip2px(getActivity(), 320)) {
            width = UIKit.dip2px(getActivity(), 320);
        }
        getDialog().getWindow().setLayout(width, getDialog().getWindow().getAttributes().height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        this.mUpdate = (Button) view.findViewById(R.id.buttom_update);
        this.mCancel = (Button) view.findViewById(R.id.buttom_cancel);
        this.mContent = (TextView) view.findViewById(R.id.umeng_update_content);
        this.mIgnore = (CheckBox) view.findViewById(R.id.umeng_update_id_check);
        this.mUpdate.setOnClickListener(this);
        this.mCancel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mResponse != null) {
            this.mContent.setText("最新版本：" + mResponse.version + "\n新版本大小：" + android.text.format.Formatter.formatFileSize(getActivity(), Long.parseLong(mResponse.target_size)) + "\n更新内容：" + mResponse.updateLog);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttom_update:
                File file = UmengUpdateAgent.downloadedFile(getActivity(), mResponse);
                if (file != null) {
                    UmengUpdateAgent.startInstall(getActivity(), file);
                } else {
                    UmengUpdateAgent.startDownload(getActivity(), mResponse);
                }
                this.dismiss();
                break;
            case R.id.buttom_cancel:
                if (mIgnore.isChecked()) {
                    UmengUpdateAgent.ignoreUpdate(getActivity(), mResponse);
                }
                this.dismiss();
                break;
        }
    }
}
