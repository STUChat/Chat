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
public class MessageDetailAdapter extends CommonAdapter<MessageDetailModel> {

    public MessageDetailAdapter(Context context, List<MessageDetailModel> datas){
        super(context,datas);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder viewHolder;
        boolean isMine = false;

        if(view != null && ((ViewHolder)view.getTag()).isMine)
            isMine = true;
        if(view == null && i % 2 == 0 || view != null && isMine && i % 2  == 0){
            view = (View)inflater.inflate(R.layout.message_detail_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.msg = (TextView)view.findViewById(R.id.msgText);
            viewHolder.photo = (ImageView)view.findViewById(R.id.photo);
            viewHolder.isMine = false;
            view.setTag(viewHolder);
        }else if(view == null && i % 2 == 1|| view !=null && isMine == false && i % 2  == 1){
            view = (View)inflater.inflate(R.layout.message_detail_item_mine, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.msg = (TextView)view.findViewById(R.id.my_msgText);
            viewHolder.photo = (ImageView)view.findViewById(R.id.my_photo);
            viewHolder.isMine = true;
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.msg.setText(datas.get(i).getMsg()+"");
        return view;
    }
    final class ViewHolder{
        ImageView photo;
        TextView msg;
        boolean isMine;
    }
}
