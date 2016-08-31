package cn.edu.stu.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import cn.edu.stu.chat.R;
import cn.edu.stu.chat.model.Constant;
import cn.edu.stu.chat.model.Friend;
import cn.edu.stu.chat.view.activity.FriendInfoActivity;

/**
 * Created by dell on 2016/8/25.
 */
public class ContactAdapter extends BaseAdapter {
    private Context context;
    private List<Friend> list;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, List list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    public void setListData(List list){
        this.list.clear();
        this.list.addAll(list);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Friend getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_contact,null);
            holder.image = (ImageView) convertView.findViewById(R.id.contact_item_photo);
            holder.name = (TextView) convertView.findViewById(R.id.contact_item_name);
            holder.layout = (LinearLayout)convertView.findViewById(R.id.contact_item_layout);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FriendInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("friend",list.get(i));
                    intent.putExtras(bundle);
                    intent.putExtra("classify", Constant.FRIEND);
                    context.startActivity(intent);
                }
            });
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name.setText(list.get(i).getName());

        return convertView;
    }

    private class ViewHolder{
        LinearLayout layout;
        ImageView image;
        TextView name;
    }
}
