package cn.edu.stu.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Terence on 2016/8/26.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    volatile public List<T> datas;
    public Context context;
    public LayoutInflater inflater;
    public CommonAdapter(Context context, List<T> datas){
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas!=null ? datas.size():0;
    }

    @Override
    public T getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
