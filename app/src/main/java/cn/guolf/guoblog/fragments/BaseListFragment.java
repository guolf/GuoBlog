package cn.guolf.guoblog.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cn.guolf.guoblog.R;
import cn.guolf.guoblog.adapter.BaseAdapter;
import cn.guolf.guoblog.data.ListDataProvider;
import cn.guolf.guoblog.processers.BaseListProcesser;
import cn.guolf.guoblog.processers.BaseProcesserImpl;

/**
 * Created by guolf on 7/17/15.
 */
public abstract class BaseListFragment<DataType, Adapter extends BaseAdapter<DataType>,Provider extends ListDataProvider<DataType,Adapter>,Processer extends BaseListProcesser<DataType,Provider>>
    extends Fragment {

    protected Processer processer;
    protected Boolean isCollection = false;
    private BaseProcesserImpl.onOptionMenuSelect menuCallBack;

    protected abstract Provider getProvider();

    public Boolean getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Boolean isCollection) {
        this.isCollection = isCollection;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(hasMenu());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(processer==null) {
            processer = createProcesser(getProvider());
        }
        processer.setMenuCallBack(menuCallBack);
        processer.setActivity((AppCompatActivity) activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout,container,false);
        processer.assumeView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        processer.loadData(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        processer.onResume();
    }

    protected abstract Processer createProcesser(Provider provider);

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        processer.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return processer.onOptionsItemSelected(item);
    }

    public boolean hasMenu(){
        return false;
    }

    @Override
    public void onDestroy() {
        processer.onDestroy();
        super.onDestroy();
    }

    public BaseListFragment setMenuCallBack(BaseProcesserImpl.onOptionMenuSelect menuCallBack){
        this.menuCallBack= menuCallBack;
        return this;
    }
}
