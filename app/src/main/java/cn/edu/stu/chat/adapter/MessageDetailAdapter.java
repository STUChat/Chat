package cn.edu.stu.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.List;

import cn.edu.stu.chat.ChatApp;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.model.MessageDetailModel;
import cn.edu.stu.chat.model.User;

/**
 * Created by Terence on 2016/8/25.
 */
public class MessageDetailAdapter extends CommonAdapter<MessageDetailModel> {

    Friend friend;
    User user;
    public MessageDetailAdapter(Context context, List<MessageDetailModel> datas,Friend friend,User user){
        super(context,datas);
        this.friend = friend;
        this.user = user;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder viewHolder;
        boolean isMine = false;
        ChatApp app = (ChatApp)context.getApplicationContext();
        boolean flag = app.getUser().getEmail().equals(datas.get(i).getUserId());
        if(view != null && ((ViewHolder)view.getTag()).isMine)
            isMine = true;
        if(view == null && !flag || view != null && isMine && !flag ){
            view = (View)inflater.inflate(R.layout.message_detail_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.msg = (TextView)view.findViewById(R.id.msgText);
            viewHolder.photo = (ImageView)view.findViewById(R.id.photo);
            viewHolder.isMine = false;
            view.setTag(viewHolder);
        }else if(view == null && flag|| view !=null && isMine == false && flag){
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
        if(viewHolder.isMine){
            Glide.with(context)
                    .load(user.getHeadUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.default_photo)
                    .into( viewHolder.photo);
        }else{
            Glide.with(context)
                    .load(friend.getHeadUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.default_photo)
                    .into( viewHolder.photo);
        }

        return view;
    }
    final class ViewHolder{
        ImageView photo;
        TextView msg;
        boolean isMine;
    }
}
