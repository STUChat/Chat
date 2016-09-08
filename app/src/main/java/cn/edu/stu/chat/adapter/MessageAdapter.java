package cn.edu.stu.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.MessageModel;

/**
 * Created by Terence on 2016/8/25.
 */
public class MessageAdapter extends CommonAdapter<MessageModel> {
    public MessageAdapter(Context context, List<MessageModel> datas){
        super(context,datas);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       final ViewHolder viewHolder;
        if(view == null){
            view = (View)inflater.inflate(R.layout.message_list_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.msgText =(TextView) view.findViewById(R.id.msgText);
            viewHolder.photo = (ImageView) view.findViewById(R.id.message_photo);
            viewHolder.nickText = (TextView)view.findViewById(R.id.nickText);
            viewHolder.timeText = (TextView)view.findViewById(R.id.timeText);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        if(datas.get(i).getMessageDetailModel()!=null) {
            viewHolder.timeText.setText(datas.get(i).getMessageDetailModel().getTime() + "");
            viewHolder.msgText.setText(datas.get(i).getMessageDetailModel().getMsg() + "");
        }
        viewHolder.nickText.setText(datas.get(i).getFriend().getName()+ "");
        Glide.with(context)
                .load(datas.get(i).getFriend().getHeadUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.default_photo)
                .into(viewHolder.photo);
        return view;
    }
    final class ViewHolder{
        ImageView photo;
        TextView msgText;
        TextView nickText;
        TextView timeText;
    }
}
