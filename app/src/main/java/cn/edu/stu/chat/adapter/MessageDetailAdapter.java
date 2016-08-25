package cn.edu.stu.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.MessageDetailModel;

/**
 * Created by Terence on 2016/8/25.
 */
public class MessageDetailAdapter extends BaseAdapter {
    private List<MessageDetailModel> datas;
    private Context context;
    private LayoutInflater inflater;
    public MessageDetailAdapter(Context context, List<MessageDetailModel> datas){
        this.datas  = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return datas.size();
    }
    @Override
    public MessageDetailModel getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder viewHolder;
        if(view == null){
            view = (View)inflater.inflate(R.layout.message_detail_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.msg = (TextView)view.findViewById(R.id.msgText);
            viewHolder.photo = (ImageView)view.findViewById(R.id.photo);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.msg.setText(datas.get(i).getMsg()+"");
        return view;
    }
    final class ViewHolder{
        ImageView photo;
        TextView msg;
    }
}
